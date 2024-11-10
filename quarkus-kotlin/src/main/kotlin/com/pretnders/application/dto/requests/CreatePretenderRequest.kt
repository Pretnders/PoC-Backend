package com.pretnders.application.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class CreatePretenderRequest(
    val nickname: String,
    val firstName: String,
    val lastName: String,
    val mail: String,
    val password: String,
    val phoneNumber: String
)
