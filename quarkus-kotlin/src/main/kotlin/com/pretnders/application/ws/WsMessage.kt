package com.pretnders.application.ws

import kotlinx.serialization.Serializable

@Serializable
data class WsMessage(val from: String, val to: String, val message: String, var action: WS_ACTIONS, var
metadata:String){
    override fun toString(): String {
        return "WsMessage(from='$from', to='$to', message='$message', action=${action.name})"
    }
}
