package com.pretnders.account_management.application

import kotlinx.serialization.Serializable

@Serializable
data class OtpRequest(val otpCode: String)
