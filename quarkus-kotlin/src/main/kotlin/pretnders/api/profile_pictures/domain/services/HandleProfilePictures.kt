package pretnders.api.profile_pictures.domain.services

import jakarta.enterprise.context.ApplicationScoped
import org.jboss.resteasy.reactive.multipart.FileUpload
import pretnders.api.azure.domain.ports.out.StorageClientOut
import pretnders.api.profile_pictures.domain.models.AddedProfilePicture
import pretnders.api.profile_pictures.domain.ports.`in`.ChangeProfilePictureCommand
import pretnders.api.profile_pictures.domain.ports.`in`.HandleProfilePicturesIn
import pretnders.api.profile_pictures.domain.ports.`in`.RemoveProfilePictureCommand
import pretnders.api.profile_pictures.domain.ports.`in`.SwapProfilePictureCommand
import pretnders.api.profile_pictures.domain.ports.out.HandleProfilePicturesOut
import pretnders.api.shared.errors.ApplicationException
import pretnders.api.shared.errors.ApplicationExceptionsEnum
import pretnders.api.shared.utils.generators.UUIDGenerator

@ApplicationScoped
class HandleProfilePictures(
    private val handleProfilePicturesOut: HandleProfilePicturesOut,
    private val storageClientOut: StorageClientOut,
    private val uuidGenerator: UUIDGenerator
): HandleProfilePicturesIn {


    override fun addProfilePicture(reference: String, phoneNumber:String, file: FileUpload): AddedProfilePicture {
        val fileName = file.fileName()
        if(!fileName.endsWith(".jpg") && !fileName.endsWith(".png")){
            throw ApplicationException(ApplicationExceptionsEnum.INVALID_FILE_TYPE)
        }
        if(file.size() > 5000){
            throw ApplicationException(ApplicationExceptionsEnum.INVALID_FILE_SIZE)
        }
        val profilePictureUrl = storageClientOut.addPretnderProfilePicture(phoneNumber, file)
        val picReference = uuidGenerator.getNewUUID()
        handleProfilePicturesOut.addPic(reference, picReference, profilePictureUrl)
        return AddedProfilePicture(picReference, profilePictureUrl)
    }

    override fun swapPicturesOrder(
        swapProfilePictureCommand: SwapProfilePictureCommand
    ) {
        if(swapProfilePictureCommand.swappedOrder == swapProfilePictureCommand.swapperOrder){
            throw ApplicationException(ApplicationExceptionsEnum.ERROR)
        }
        if(swapProfilePictureCommand.swappedOrder < 0 || swapProfilePictureCommand.swapperOrder < 0){
            throw ApplicationException(ApplicationExceptionsEnum.ERROR)
        }
        if(swapProfilePictureCommand.swappedOrder > 9 || swapProfilePictureCommand.swapperOrder > 9){
            throw ApplicationException(ApplicationExceptionsEnum.ERROR)
        }
        handleProfilePicturesOut.swapPicturesOrder(
            swapProfilePictureCommand.swapperReference,
            swapProfilePictureCommand.swapperOrder,
            swapProfilePictureCommand.swappedReference,
            swapProfilePictureCommand.swappedOrder)
    }

    override fun removeProfilePicture(removeProfilePictureCommand: RemoveProfilePictureCommand) {
        storageClientOut.deleteBlobFromContainer(
            removeProfilePictureCommand.phoneNumber,
            removeProfilePictureCommand.blobName
        )
        handleProfilePicturesOut.deletePicture(removeProfilePictureCommand.profilePictureReference)
    }

    override fun changeProfilePicture(changeProfilePictureCommand: ChangeProfilePictureCommand):String {
        storageClientOut.deleteBlobFromContainer(changeProfilePictureCommand.phoneNumber, changeProfilePictureCommand.blobName)
        val url = storageClientOut.addPretnderProfilePicture(changeProfilePictureCommand.phoneNumber, changeProfilePictureCommand.file)
        handleProfilePicturesOut.updatePictureUrl(changeProfilePictureCommand.profilePictureReference, url)
        return url
    }
}