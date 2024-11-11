package com.pretnders.persistence.services.profile_pics

import com.pretnders.domain.ports.out.ProfilePicturesOut
import com.pretnders.persistence.entities.PretndersEntity
import com.pretnders.persistence.entities.ProfilePicsEntity
import com.pretnders.persistence.repositories.PretendersRepository
import com.pretnders.persistence.repositories.ProfilePicsRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class ProfilePicsSpi:ProfilePicturesOut {
    @Inject
    private lateinit var profilePicsRepository: ProfilePicsRepository

    @Inject
    private lateinit var findPretendersRepository: PretendersRepository


    override fun getNextPicOrder(reference:String): Short {
        return profilePicsRepository.findNextPicOrder(reference)
    }

    override fun addPic(pretnderReference: String, pictureReference: String, profilePicUrl: String) {
        val pretndersEntity = PretndersEntity()
        pretndersEntity.id =findPretendersRepository.findIDByReference(pretnderReference)
        val newProfilePicEntity = ProfilePicsEntity()
        newProfilePicEntity.reference = pictureReference
        newProfilePicEntity.order = getNextPicOrder(pretnderReference)
        newProfilePicEntity.url = profilePicUrl
        newProfilePicEntity.pretnders = pretndersEntity
        profilePicsRepository.persistAndFlush(newProfilePicEntity)
    }
}