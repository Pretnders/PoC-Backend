package com.pretnders.application.dto.responses

import kotlinx.serialization.Serializable

@Serializable
data class PretnderDetailsResponse(
    val height: String,
    val bodyType: String,
    val diet: String,
    val beliefs: String,
    val smokes: String,
    val drinks: String,
    val socialStatus: String,
    val biography: String,
    val city: String,
    val country: String
)

