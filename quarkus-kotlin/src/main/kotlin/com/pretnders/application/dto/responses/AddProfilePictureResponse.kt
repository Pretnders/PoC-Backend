package com.pretnders.application.dto.responses

import kotlinx.serialization.Serializable

@Serializable
data class AddProfilePictureResponse(val reference: String, val url:String)