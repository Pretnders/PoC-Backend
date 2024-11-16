package pretnders.api.profile_pictures.persistence.services

import pretnders.api.profile_pictures.domain.ports.out.HandleProfilePicturesOut
import pretnders.api.pretnders.persistence.entities.PretndersEntity
import pretnders.api.pretnders.persistence.repositories.PretndersQueryRepository
import pretnders.api.profile_pictures.persistence.entities.ProfilePicsEntity
import pretnders.api.profile_pictures.persistence.repositories.FindProfilePicsRepository
import pretnders.api.profile_pictures.persistence.repositories.ManageProfilePicsRepository
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional

@ApplicationScoped
class ProfilePicsHandler : HandleProfilePicturesOut {
    @Inject
    private lateinit var manageProfilePicsRepository: ManageProfilePicsRepository
    @Inject
    private lateinit var findProfilePicsRepository: FindProfilePicsRepository
    @Inject
    private lateinit var pretndersQueryRepository: PretndersQueryRepository

    override fun getNextPicOrder(pretnderId: Long): Long {
        return findProfilePicsRepository.findNextPicOrder(pretnderId)
    }

    @Transactional
    override fun addPic(pretnderReference: String, pictureReference: String, profilePicUrl: String) {
        val pretnderID = pretndersQueryRepository.findIDByReference(pretnderReference)
        Log.info(pretnderID.toString())
        val pretndersEntity = PretndersEntity()
        pretndersEntity.id = pretnderID
        val newProfilePicEntity = ProfilePicsEntity()
        newProfilePicEntity.reference = pictureReference
        newProfilePicEntity.order = getNextPicOrder(pretnderID).toShort()
        newProfilePicEntity.url = profilePicUrl
        newProfilePicEntity.pretnder = pretndersEntity
        manageProfilePicsRepository.persistAndFlush(newProfilePicEntity)
    }

    @Transactional
    override fun swapPicturesOrder(
        swapperReference: String,
        swapperOrder: Long,
        swappedReference: String,
        swappedOrder: Long
    ) {
        manageProfilePicsRepository.swapPicturesOrder(swapperReference, swapperOrder, swappedReference, swappedOrder)
    }

    @Transactional
    override fun deletePicture(pictureReference: String) {
        val picturesInDb = findProfilePicsRepository.findPretnderPictureListByPictureReference(pictureReference)
        val toRemove = picturesInDb.find { item -> item.reference == pictureReference }
        val removedPicOrder = toRemove?.order!!
        for(profilePicEntity in picturesInDb) {
            if(profilePicEntity.order > removedPicOrder){
                profilePicEntity.order = (profilePicEntity.order - 1).toShort()
                manageProfilePicsRepository.setPictureOrderById(profilePicEntity.order, profilePicEntity.id)
            }
        }
        manageProfilePicsRepository.deleteById(toRemove.id)
    }

    @Transactional
    override fun updatePictureUrl(pictureReference: String, newUrl: String) {
        manageProfilePicsRepository.changePictureUrl(pictureReference,newUrl)
    }
}