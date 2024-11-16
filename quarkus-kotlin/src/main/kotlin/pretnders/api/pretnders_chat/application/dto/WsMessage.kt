package pretnders.api.pretnders_chat.application.dto

import kotlinx.serialization.Serializable

@Serializable
data class WsMessage(val from: String, val to: String, var content: String, var action: WsPretndersChatActions, var
metadata:String){
    override fun toString(): String {
        return "WsMessage(from='$from', to='$to', content='$content', action=${action.name})"
    }
}
