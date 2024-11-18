package pretnders.api.pretnders_chat.application.ws_entrypoint

import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.websocket.*
import jakarta.websocket.server.PathParam
import jakarta.websocket.server.ServerEndpoint
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromString
import org.jsoup.Jsoup
import org.jsoup.safety.Safelist
import pretnders.api.pretnders_chat.application.dto.WsMessage
import pretnders.api.pretnders_chat.application.dto.WsPretndersChatActions
import pretnders.api.pretnders_chat.persistence.MessagesEntity
import pretnders.api.pretnders_chat.persistence.MessagesRepository
import pretnders.api.shared.utils.generators.UUIDGenerator
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

@ApplicationScoped
@ServerEndpoint("/messages/{reference}")
class ChatWSEndpoint (
    private val messagesRepository: MessagesRepository,
    private val uuidGenerator: UUIDGenerator
) {

    private val sessions: MutableMap<String, Session> = ConcurrentHashMap()


    @OnOpen
    fun onOpen(session: Session?, @PathParam("reference") reference: String) {
        Log.debug("onOpen> $reference")
        sessions[reference] = session!!
    }

    @OnClose
    fun onClose(@PathParam("reference") reference: String) {
        Log.debug("onClose> $reference")
        sessions.remove(reference)
    }

    @OnError
    fun onError(@PathParam("reference") reference: String, throwable: Throwable) {
        Log.info("onError> $reference: $throwable")
    }

    @OnMessage
    fun onMessage(message: String, @PathParam("reference") reference: String) {
        try {
            val wsMessage = decodeFromString<WsMessage>(message)
            Log.info("onMessage> $reference: ${wsMessage.content}")
            when (wsMessage.action) {
                WsPretndersChatActions.SEND_MESSAGE -> {
                    if (wsMessage.content.length >= 2) {
                        val messageReference = uuidGenerator.getNewUUID()
                        val wsMessageResponse = WsMessage(
                            wsMessage.from,
                            wsMessage.to,
                            wsMessage.content,
                            WsPretndersChatActions.SEND_MESSAGE_RESPONSE,
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
                        broadcast(wsMessage.from, Json.encodeToString(wsMessageResponse))
                    }
                }
                WsPretndersChatActions.DISCONNECT -> {
                    sessions[reference]?.close()
                }
                WsPretndersChatActions.REPORT_MESSAGE -> {
                    Log.info("Reporting message ${wsMessage.metadata}")
                    CompletableFuture.runAsync {
                        messagesRepository.reportMessage(wsMessage.metadata)
                    }
                    wsMessage.action = WsPretndersChatActions.REPORT_MESSAGE_RESPONSE
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
