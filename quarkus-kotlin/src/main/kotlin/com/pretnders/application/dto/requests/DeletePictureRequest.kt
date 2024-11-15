package com.pretnders.application.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class DeletePictureRequest(val reference:String, val blobName:String)
