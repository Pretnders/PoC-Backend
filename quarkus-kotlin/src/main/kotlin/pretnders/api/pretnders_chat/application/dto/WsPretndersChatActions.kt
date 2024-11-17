package pretnders.api.pretnders_chat.application.dto

enum class WsPretndersChatActions {
    SEND_MESSAGE,
    SEND_MESSAGE_RESPONSE,
    DISCONNECT,
    GET_MESSAGE_METADATA,
    REPORT_MESSAGE,
    REPORT_MESSAGE_RESPONSE
}