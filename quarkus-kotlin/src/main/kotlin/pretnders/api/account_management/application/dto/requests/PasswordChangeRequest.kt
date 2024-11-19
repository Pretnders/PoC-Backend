package pretnders.api.account_management.application.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class PasswordChangeRequest(val currentPassword:String, val password:String, val passwordConfirmation:String)
