package com.pretnders.admins.application

import kotlinx.serialization.Serializable

@Serializable
data class CreateAdminRequest(
    val nickname: String,
    val mail: String,
    val password: String,
    val phoneNumber: String,
    val adminCode: String,
)
