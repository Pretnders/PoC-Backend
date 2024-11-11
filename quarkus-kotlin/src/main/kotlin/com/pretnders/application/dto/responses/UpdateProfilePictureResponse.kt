package com.pretnders.application.dto.responses

import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfilePictureResponse(val profilePictureUrl:String, var reference:String ? = null)
