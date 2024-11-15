package com.pretnders.application.dto.requests

import kotlinx.serialization.Serializable
import org.jboss.resteasy.reactive.RestForm
import org.jboss.resteasy.reactive.multipart.FileUpload

@Serializable
data class UpdatePictureRequest(val reference:String, val blobName:String, @RestForm("image") val image:
FileUpload
)
