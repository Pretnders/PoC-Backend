package com.pretnders.application.ws

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
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap


@ApplicationScoped
@ServerEndpoint("/messages/{reference}")
class ChatWSResource {
    val sessions: MutableMap<String, Session> = ConcurrentHashMap()

    @Inject
    private lateinit var messagesRepository: MessagesRepository

    @OnOpen
    fun onOpen(session: Session?, @PathParam("reference") reference: String) {
        sessions[reference] = session!!
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
            val wsMessage = Json.decodeFromString<WsMessage>(message)
            Log.info("onMessage> $reference: ${wsMessage.message}")
            if(wsMessage.action == WS_ACTIONS.SEND_MESSAGE){
                if(wsMessage.message.length > 2){
                    broadcast(wsMessage.to, message)
                    CompletableFuture.runAsync {
                        val entity = MessagesEntity()
                        entity.content = wsMessage.message
                        entity.reported = false
                        val res = messagesRepository.add(entity, wsMessage.to, wsMessage.from)
                        wsMessage.action = WS_ACTIONS.GET_MESSAGE_METADATA
                        wsMessage.metadata = res.toString()
                        broadcast(wsMessage.to, Json.encodeToString(wsMessage))
                    }.exceptionally { throwable ->
                        Log.error("Error handling message: ${throwable.message}", throwable)
                        null
                    }
                }
            } else if( wsMessage.action == WS_ACTIONS.DISCONNECT){
                sessions[reference]?.close()
            }
        }catch (e:Exception){
            Log.info("Failed to parse JSON: ${e.message}")
        }
    }

    private fun broadcast(to:String, message: String) {
        sessions[to]?.asyncRemote?.sendText(message)
    }
}
