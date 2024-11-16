package com.pretnders.profile_pictures.persistence.services

import com.pretnders.profile_pictures.domain.ports.out.HandleProfilePicturesOut
import com.pretnders.pretnders.persistence.entities.PretndersEntity
import com.pretnders.pretnders.persistence.repositories.PretndersRepository
import com.pretnders.profile_pictures.persistence.entities.ProfilePicsEntity
import com.pretnders.profile_pictures.persistence.models.projections.ProfilePicNoPretnder
import com.pretnders.profile_pictures.persistence.repositories.ProfilePicsRepository
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.persistence.LockModeType
import jakarta.transaction.Transactional
import kotlinx.serialization.Serializable

@ApplicationScoped
class ProfilePicsHandler : HandleProfilePicturesOut {
    @Inject
    private lateinit var profilePicsRepository: ProfilePicsRepository

    @Inject
    private lateinit var findPretndersRepository: PretndersRepository


    override fun getNextPicOrder(pretnderId: Long): Long {
        return profilePicsRepository.findNextPicOrder(pretnderId)
    }

    @Transactional
    override fun addPic(pretnderReference: String, pictureReference: String, profilePicUrl: String) {
        val pretnderID = findPretndersRepository.findIDByReference(pretnderReference)
        Log.info(pretnderID.toString())
        val pretndersEntity = PretndersEntity()
        pretndersEntity.id = pretnderID
        val newProfilePicEntity = ProfilePicsEntity()
        newProfilePicEntity.reference = pictureReference
        newProfilePicEntity.order = getNextPicOrder(pretnderID).toShort()
        newProfilePicEntity.url = profilePicUrl
        newProfilePicEntity.pretnder = pretndersEntity
        profilePicsRepository.persistAndFlush(newProfilePicEntity)
    }

    @Transactional
    override fun swapPicturesOrder(
        swapperReference: String,
        swapperOrder: Long,
        swappedReference: String,
        swappedOrder: Long
    ) {
        Log.info(
            "??"
        )
        profilePicsRepository.update(
            "SET order = :swapperOrder WHERE reference = :swappedReference", mapOf(
                "swappedReference" to swappedReference,
                "swapperOrder" to swapperOrder,
            )
        )
        profilePicsRepository.update(
            "SET order = :swappedOrder WHERE reference = :swapperReference", mapOf(
                "swapperReference" to swapperReference,
                "swappedOrder" to swappedOrder,
            )
        )
    }

    @Transactional
    override fun deletePicture(pictureReference: String) {
        val picturesInDb = profilePicsRepository.find(
            "SELECT id, order, reference FROM ProfilePicsEntity WHERE pretnder.id = (SELECT DISTINCT pretnder.id FROM" +
                    " ProfilePicsEntity WHERE reference = :pictureReference) ORDER BY order",
            mapOf(
                "pictureReference" to pictureReference
            )
        ).withLock<ProfilePicsEntity>(LockModeType.PESSIMISTIC_WRITE).project(ProfilePicNoPretnder::class.java)
            .list<ProfilePicNoPretnder>()
        Log.info(picturesInDb.toString())
        val toRemove = picturesInDb.find { item -> item.reference == pictureReference }
        val removedPicOrder = toRemove?.order!!
        for(profilePicEntity in picturesInDb) {
            if(profilePicEntity.order > removedPicOrder){
                profilePicEntity.order = (profilePicEntity.order - 1).toShort()
                profilePicsRepository.update("SET order = :newPicOrder WHERE id = :picId", mapOf(
                    "newPicOrder" to profilePicEntity.order,
                    "picId" to profilePicEntity.id
                ))
            }
        }
        profilePicsRepository.delete("WHERE id = :toRemoveID", mapOf(
            "toRemoveID" to toRemove.id
        ))
    }

    @Transactional
    override fun updatePictureUrl(pictureReference: String, newUrl: String) {
        profilePicsRepository.update("SET url = :newUrl WHERE reference = :pictureReference", mapOf(
            "pictureReference" to pictureReference,
            "newUrl" to newUrl
        ))
    }

    @Serializable
    class Res(var id:Long, var order:Short, var reference:String)
}