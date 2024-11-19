package pretnders.api.account_management.application.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class PasswordRecoveryRequest(val mail:String, val token:String, val password:String, val
passwordConfirmation:String)
