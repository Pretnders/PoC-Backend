package com.pretnders.domain.services

import com.pretnders.domain.ports.`in`.ProfilePicturesIn
import com.pretnders.domain.ports.out.FindPretendersOut
import com.pretnders.domain.ports.out.ProfilePicturesOut
import com.pretnders.domain.utils.UUIDGenerator.getNewUUID
import com.pretnders.persistence.entities.PretndersEntity
import com.pretnders.persistence.entities.ProfilePicsEntity
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class ProfilePicturesService:ProfilePicturesIn {

    @Inject
    private lateinit var findPretendersOut: FindPretendersOut


    @Inject
    private lateinit var profilePicturesOut: ProfilePicturesOut
    override fun addProfilePicture(reference: String, profilePictureUrl: String) {
        val picOrder = profilePicturesOut.getNextPicOrder(reference)
        val picReference = getNewUUID()
        profilePicturesOut.addPic(reference, picReference, profilePictureUrl, picOrder)


    }

}