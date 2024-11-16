package com.pretnders.profile_pictures.application.dto.responses

import kotlinx.serialization.Serializable

@Serializable
data class ProfilePictureResponse(val reference: String, val url:String,val order:Short)
