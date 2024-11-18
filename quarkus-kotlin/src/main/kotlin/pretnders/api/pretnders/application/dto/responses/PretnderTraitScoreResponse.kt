package pretnders.api.pretnders.application.dto.responses

import kotlinx.serialization.Serializable

@Serializable
data class PretnderTraitScoreResponse(
    val reference:String, val trait:String,val mirrorTrait:String, val description:String,
    val score: Short
)
