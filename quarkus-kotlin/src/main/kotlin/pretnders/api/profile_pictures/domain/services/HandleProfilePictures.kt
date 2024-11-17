package pretnders.api.profile_pictures.domain.services

import io.quarkus.logging.Log
import pretnders.api.profile_pictures.domain.models.AddedProfilePicture
import pretnders.api.azure.domain.ports.out.StorageClientOut
import pretnders.api.profile_pictures.domain.ports.`in`.HandleProfilePicturesIn
import pretnders.api.profile_pictures.domain.ports.out.HandleProfilePicturesOut
import pretnders.api.shared.utils.generators.UUIDGenerator.getNewUUID
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
        Log.info("nani ?")
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