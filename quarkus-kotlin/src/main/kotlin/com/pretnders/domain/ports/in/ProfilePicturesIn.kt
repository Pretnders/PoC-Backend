package com.pretnders.domain.ports.`in`

import com.pretnders.domain.models.profile_pics.AddedProfilePicture
import org.jboss.resteasy.reactive.multipart.FileUpload

interface ProfilePicturesIn {
    fun addProfilePicture(reference: String, phoneNumber:String, file: FileUpload): AddedProfilePicture
    fun swapPicturesOrder(swapperReference:String, swapperOrder:Long, swappedReference:String, swappedOrder:Long)
}