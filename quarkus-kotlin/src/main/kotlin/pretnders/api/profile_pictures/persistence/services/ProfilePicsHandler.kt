package pretnders.api.profile_pictures.persistence.services

import pretnders.api.profile_pictures.domain.ports.out.HandleProfilePicturesOut
import pretnders.api.pretnders.persistence.entities.PretndersEntity
import pretnders.api.pretnders.persistence.repositories.PretndersQueryRepository
import pretnders.api.profile_pictures.persistence.entities.ProfilePicsEntity
import pretnders.api.profile_pictures.persistence.repositories.ProfilePicsQueryRepository
import pretnders.api.profile_pictures.persistence.repositories.ProfilePicsCommandsRepository
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional

@ApplicationScoped
class ProfilePicsHandler : HandleProfilePicturesOut {
    @Inject
    private lateinit var profilePicsCommandsRepository: ProfilePicsCommandsRepository
    @Inject
    private lateinit var profilePicsQueryRepository: ProfilePicsQueryRepository
    @Inject
    private lateinit var pretndersQueryRepository: PretndersQueryRepository

    override fun getNextPicOrder(pretnderId: Long): Long {
        return profilePicsQueryRepository.findNextPicOrder(pretnderId)
    }

    @Transactional
    override fun addPic(pretnderReference: String, pictureReference: String, profilePicUrl: String) {
        val pretnderID = pretndersQueryRepository.findIDByReference(pretnderReference)
        Log.info(pretnderID.toString())
        val pretndersEntity = PretndersEntity()
        pretndersEntity.id = pretnderID
        val newProfilePicEntity = ProfilePicsEntity()
        newProfilePicEntity.reference = pictureReference
        newProfilePicEntity.order = pretnderID?.toShort()
        newProfilePicEntity.url = profilePicUrl
        newProfilePicEntity.pretnder = pretndersEntity
        profilePicsCommandsRepository.persistAndFlush(newProfilePicEntity)
    }

    @Transactional
    override fun swapPicturesOrder(
        swapperReference: String,
        swapperOrder: Long,
        swappedReference: String,
        swappedOrder: Long
    ) {
        profilePicsCommandsRepository.swapPicturesOrder(swapperReference, swapperOrder, swappedReference, swappedOrder)
    }

    @Transactional
    override fun deletePicture(pictureReference: String) {
        val picturesInDb = profilePicsQueryRepository.findPretnderPictureListByPictureReference(pictureReference)
        val toRemove = picturesInDb.find { item -> item.reference == pictureReference }
        val removedPicOrder = toRemove?.order!!
        for(profilePicEntity in picturesInDb) {
            if(profilePicEntity.order > removedPicOrder){
                profilePicEntity.order = (profilePicEntity.order - 1).toShort()
                profilePicsCommandsRepository.setPictureOrderById(profilePicEntity.order, profilePicEntity.id)
            }
        }
        profilePicsCommandsRepository.deleteById(toRemove.id)
    }

    @Transactional
    override fun updatePictureUrl(pictureReference: String, newUrl: String) {
        profilePicsCommandsRepository.changePictureUrl(pictureReference,newUrl)
    }
}