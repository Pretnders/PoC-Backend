package pretnders.api.profile_pictures.domain.ports.`in`

import pretnders.api.profile_pictures.domain.models.AddedProfilePicture
import org.jboss.resteasy.reactive.multipart.FileUpload

interface HandleProfilePicturesIn {
    fun addProfilePicture(reference: String, phoneNumber:String, file: FileUpload): AddedProfilePicture
    fun swapPicturesOrder(swapperReference:String, swapperOrder:Long, swappedReference:String, swappedOrder:Long)
    fun deleteProfilePicture(reference: String, blobName:String, phoneNumber:String)
    fun updateProfilePicture(reference: String, blobName:String, file:FileUpload, phoneNumber:String):String

}