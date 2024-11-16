package pretnders.api.pretnders.application.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class ChangeNicknameRequest(
    val newNickname: String
)
