package com.templates.application.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class OtpRequest(val otpCode: String)
