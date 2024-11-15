package com.pretnders.domain.services

import com.pretnders.domain.models.profile_pics.AddedProfilePicture
import com.pretnders.domain.ports.`in`.AzureStorageIn
import com.pretnders.domain.ports.`in`.ProfilePicturesIn
import com.pretnders.domain.ports.out.ProfilePicturesOut
import com.pretnders.domain.utils.UUIDGenerator.getNewUUID
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.jboss.resteasy.reactive.multipart.FileUpload

@ApplicationScoped
class ProfilePictures:ProfilePicturesIn {
    @Inject
    private lateinit var profilePicturesOut: ProfilePicturesOut

    @Inject
    private lateinit var azureStorageIn: AzureStorageIn

    override fun addProfilePicture(reference: String, phoneNumber:String, file: FileUpload):AddedProfilePicture {
        val profilePictureUrl = azureStorageIn.addPretnderProfilePicture(phoneNumber, file)
        val picReference = getNewUUID()
        profilePicturesOut.addPic(reference, picReference, profilePictureUrl)
        return AddedProfilePicture(picReference, profilePictureUrl)
    }

    override fun swapPicturesOrder(
        swapperReference: String,
        swapperOrder: Long,
        swappedReference: String,
        swappedOrder: Long
    ) {
        profilePicturesOut.swapPicturesOrder(swapperReference, swapperOrder, swappedReference, swappedOrder)
    }
}