package com.pretnders.persistence.services.profile_pics

import com.pretnders.domain.ports.out.ProfilePicturesOut
import com.pretnders.persistence.entities.PretndersEntity
import com.pretnders.persistence.entities.ProfilePicsEntity
import com.pretnders.persistence.repositories.PretndersRepository
import com.pretnders.persistence.repositories.ProfilePicsRepository
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional

@ApplicationScoped
class ProfilePicsSpi:ProfilePicturesOut {
    @Inject
    private lateinit var profilePicsRepository: ProfilePicsRepository

    @Inject
    private lateinit var findPretndersRepository: PretndersRepository


    override fun getNextPicOrder(pretnderId:Long): Long {
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
        profilePicsRepository.update("SET order = :swapperOrder WHERE reference = :swappedReference", mapOf(
            "swappedReference" to swappedReference,
            "swapperOrder" to swapperOrder,
        ))
        profilePicsRepository.update("SET order = :swappedOrder WHERE reference = :swapperReference", mapOf(
            "swapperReference" to swapperReference,
            "swappedOrder" to swappedOrder,
        ))
    }
}