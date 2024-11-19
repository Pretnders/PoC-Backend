package pretnders.api.account_management.application.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class OtpRequest(val otpCode: String)
