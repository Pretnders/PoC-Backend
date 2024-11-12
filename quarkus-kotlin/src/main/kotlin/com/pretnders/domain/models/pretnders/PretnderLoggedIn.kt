package com.pretnders.domain.models.pretnders

import com.pretnders.domain.models.profile_pics.ProfilePicture

data class PretnderLoggedIn(
    val nickname:String,
    val firstName: String,
    val lastName: String,
    val mail: String,
    val phoneNumber: String,
    val reference: String,
    val jwToken:String,
    val accountVerifiedStatus:Boolean,
    val profilePictures:List<ProfilePicture> ? = ArrayList(),
    val pretnderDetails: PretnderDetails

){
    override fun toString(): String {
        return "PretnderLoggedIn(nickname='$nickname', firstName='$firstName', lastName='$lastName', mail='$mail', phoneNumber='$phoneNumber', reference='$reference', jwToken='$jwToken', accountVerifiedStatus=$accountVerifiedStatus, profilePictures=$profilePictures, pretnderDetails=$pretnderDetails)"
    }
}
