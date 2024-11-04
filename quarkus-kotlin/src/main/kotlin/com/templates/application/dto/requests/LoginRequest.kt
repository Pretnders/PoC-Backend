package com.templates.application.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(val identifier: String, val password: String)
