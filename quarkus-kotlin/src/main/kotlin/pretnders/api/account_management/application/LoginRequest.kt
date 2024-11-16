package pretnders.api.account_management.application

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(val identifier: String, val password: String)
