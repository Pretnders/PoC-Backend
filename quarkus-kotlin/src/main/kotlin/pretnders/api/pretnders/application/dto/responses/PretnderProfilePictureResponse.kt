package pretnders.api.pretnders.application.dto.responses

import kotlinx.serialization.Serializable

@Serializable
data class PretnderProfilePictureResponse(val reference: String, val url:String, val order:Short)
