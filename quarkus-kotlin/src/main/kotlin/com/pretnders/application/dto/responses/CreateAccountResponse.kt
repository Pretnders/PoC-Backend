package com.pretnders.application.dto.responses

import kotlinx.serialization.Serializable

@Serializable
data class CreateAccountResponse(val reference: String,
                                 val accountVerifiedStatus:Boolean)
