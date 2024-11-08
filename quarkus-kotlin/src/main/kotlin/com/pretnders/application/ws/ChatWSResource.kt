package com.pretnders.application.ws

import com.pretnders.domain.utils.UUIDGenerator.getNewUUID
import com.pretnders.persistence.entities.MessagesEntity
import com.pretnders.persistence.repositories.MessagesRepository
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.websocket.*
import jakarta.websocket.server.PathParam
import jakarta.websocket.server.ServerEndpoint
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromString
import org.jsoup.Jsoup
import org.jsoup.safety.Safelist
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

@ApplicationScoped
@ServerEndpoint("/messages/{reference}")
class ChatWSResource {
    private val sessions: MutableMap<String, Session> = ConcurrentHashMap()

    @Inject
    private lateinit var messagesRepository: MessagesRepository

    @OnOpen
    fun onOpen(session: Session?, @PathParam("reference") reference: String) {
        sessions.put(reference, session!!)
        Log.info(sessions.size)
        Log.info("onOpen> $reference")
    }

    @OnClose
    fun onClose(session: Session?, @PathParam("reference") reference: String) {
        Log.info("onClose> $reference")


        sessions.remove(reference)
    }

    @OnError
    fun onError(session: Session?, @PathParam("reference") reference: String, throwable: Throwable) {
        Log.info("onError> $reference: $throwable")
    }

    @OnMessage
    fun onMessage(message: String, @PathParam("reference") reference: String) {
        try {
            val wsMessage = decodeFromString<WsMessage>(message)
            Log.info("onMessage> $reference: ${wsMessage.content}")
            when (wsMessage.action) {
                WS_ACTIONS.SEND_MESSAGE -> {
                    if (wsMessage.content.length >= 2) {
                        val messageReference = getNewUUID()
                        val wsMessageResponse = WsMessage(
                            wsMessage.from,
                            wsMessage.to,
                            wsMessage.content,
                            WS_ACTIONS.SEND_MESSAGE_RESPONSE,
                            messageReference
                        )
                        wsMessage.metadata = messageReference
                        CompletableFuture.runAsync {
                            val entity = MessagesEntity()
                            entity.match = 1L
                            entity.content = Jsoup.clean(wsMessage.content, Safelist.basic())
                            entity.reference = messageReference
                            entity.senderReference = wsMessage.from
                            messagesRepository.add(entity)
                        }
                        broadcast(wsMessage.to, Json.encodeToString(wsMessage))
                        wsMessage.action = WS_ACTIONS.SEND_MESSAGE_RESPONSE
                        broadcast(wsMessage.from, Json.encodeToString(wsMessageResponse))
                        Log.info("done")

                    }
                }
                WS_ACTIONS.DISCONNECT -> {
                    sessions[reference]?.close()
                }
                WS_ACTIONS.REPORT_MESSAGE -> {
                    Log.info("Reporting message ${wsMessage.metadata}")
                    CompletableFuture.runAsync {
                        messagesRepository.reportMessage(wsMessage.metadata)
                    }
                    wsMessage.action = WS_ACTIONS.REPORT_MESSAGE_RESPONSE
                    wsMessage.content = "Message reported"
                    broadcast(wsMessage.from, Json.encodeToString(wsMessage))
                }

                else -> return
            }
        } catch (e: Exception) {
            Log.error("Failed to parse JSON: ${e.message}")
            Log.error("Origin message: $message")
        }
    }

    private fun broadcast(to: String, message: String) {
        Log.info(to)
        sessions[to]?.asyncRemote?.sendText(message)
    }
}
