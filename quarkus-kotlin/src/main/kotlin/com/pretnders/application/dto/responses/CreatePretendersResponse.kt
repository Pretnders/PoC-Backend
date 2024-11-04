package com.pretnders.application.dto.responses

import kotlinx.serialization.Serializable

@Serializable
data class CreatePretendersResponse(val reference: String,
                                    val accountVerifiedStatus:Boolean)
