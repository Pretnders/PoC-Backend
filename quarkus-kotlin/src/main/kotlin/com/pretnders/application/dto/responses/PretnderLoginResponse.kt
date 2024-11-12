package com.pretnders.application.dto.responses

import kotlinx.serialization.Serializable

@Serializable
data class PretnderLoginResponse(
    val nickname:String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val mail:String,
    val accountVerifiedStatus: Boolean,
    val pretnderDetails:PretnderDetailsResponse,
    val profilePictures:List<ProfilePictureResponse>
){
    override fun toString(): String {
        return "PretnderLoginResponse(nickname='$nickname', firstName='$firstName', lastName='$lastName', phoneNumber='$phoneNumber', mail='$mail', accountVerifiedStatus=$accountVerifiedStatus, profilePictures=$profilePictures)"
    }
}
