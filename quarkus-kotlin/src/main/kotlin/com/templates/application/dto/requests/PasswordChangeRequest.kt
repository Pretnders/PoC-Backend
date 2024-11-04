package com.templates.application.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class PasswordChangeRequest(val password:String, val passwordConfirmation:String)
