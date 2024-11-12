package com.pretnders.application.dto.responses

import kotlinx.serialization.Serializable

@Serializable
data class ProfilePictureResponse(val reference: String, val url: String, val order:Short){
    override fun toString(): String {
        return "ProfilePicture(reference='$reference', url='$url', order=$order)"
    }
}