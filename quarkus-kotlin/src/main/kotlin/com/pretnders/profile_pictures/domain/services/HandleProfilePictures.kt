package com.pretnders.profile_pictures.domain.services

import com.pretnders.profile_pictures.domain.models.AddedProfilePicture
import com.pretnders.azure.domain.ports.out.StorageClientOut
import com.pretnders.profile_pictures.domain.ports.`in`.HandleProfilePicturesIn
import com.pretnders.profile_pictures.domain.ports.out.HandleProfilePicturesOut
import com.pretnders.shared.utils.generators.UUIDGenerator.getNewUUID
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.jboss.resteasy.reactive.multipart.FileUpload

@ApplicationScoped
class HandleProfilePictures: HandleProfilePicturesIn {
    @Inject
    private lateinit var profilePicturesOut: HandleProfilePicturesOut

    @Inject
    private lateinit var storageClientOut: StorageClientOut

    override fun addProfilePicture(reference: String, phoneNumber:String, file: FileUpload): AddedProfilePicture {
        val profilePictureUrl = storageClientOut.addPretnderProfilePicture(phoneNumber, file)
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

    override fun deleteProfilePicture(reference: String, blobName: String, phoneNumber: String) {
        storageClientOut.deleteBlobFromContainer(phoneNumber, blobName)
        profilePicturesOut.deletePicture(reference)
    }

    override fun updateProfilePicture(reference: String, blobName: String, file: FileUpload, phoneNumber: String):String {
        storageClientOut.deleteBlobFromContainer(phoneNumber, blobName)
        val url = storageClientOut.addPretnderProfilePicture(phoneNumber, file)
        profilePicturesOut.updatePictureUrl(reference, url)
        return url
    }
}