package com.pretnders.pretnders.application.dto.responses

import com.pretnders.profile_pictures.application.dto.responses.ProfilePictureResponse
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val nickname:String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val mail:String,
    val accountVerifiedStatus: Boolean,
    val pretnderDetails: FindDetailsResponse,
    var profilePictures: List<ProfilePictureResponse>? = ArrayList(),
    var traitPairs: List<FindTraitScoreResponse>
){
    override fun toString(): String {
        return "PretnderLoginResponse(nickname='$nickname', firstName='$firstName', lastName='$lastName', phoneNumber='$phoneNumber', mail='$mail', accountVerifiedStatus=$accountVerifiedStatus, pretnderDetails=$pretnderDetails, profilePictures=$profilePictures, traitPairs=$traitPairs)"
    }
}
