package com.pretnders.application.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class UpdateNicknameRequest(
    val newNickname: String
)
