package pretnders.api.profile_pictures.domain.ports.`in`

import pretnders.api.profile_pictures.domain.models.AddedProfilePicture
import org.jboss.resteasy.reactive.multipart.FileUpload

interface HandleProfilePicturesIn {
    fun addProfilePicture(reference: String, phoneNumber:String, file: FileUpload): AddedProfilePicture
    fun swapPicturesOrder(swapProfilePictureCommand: SwapProfilePictureCommand)
    fun removeProfilePicture(removeProfilePictureCommand: RemoveProfilePictureCommand)
    fun changeProfilePicture(changeProfilePictureCommand: ChangeProfilePictureCommand):String

}

data class RemoveProfilePictureCommand(
    val profilePictureReference: String,
    val blobName:String,
    val phoneNumber:String
)

data class ChangeProfilePictureCommand(
    val profilePictureReference: String,
    val blobName:String,
    val file: FileUpload,
    val phoneNumber:String
)

data class SwapProfilePictureCommand(
    val swapperReference:String,
    val swapperOrder:Long,
    val swappedReference:String,
    val swappedOrder:Long
)