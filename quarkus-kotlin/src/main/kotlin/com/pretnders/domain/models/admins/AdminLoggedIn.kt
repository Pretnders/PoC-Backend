package com.pretnders.domain.models.admins

class AdminLoggedIn(
    val nickname:String,
    val mail: String,
    val phoneNumber: String,
    val reference: String,
    val jwToken:String,
    val profilePictureUrl:String? = null,
    val accountVerifiedStatus:Boolean
){
    override fun toString(): String {
        return "AdminLoggedIn(nickname='$nickname', mail='$mail', phoneNumber='$phoneNumber', reference='$reference', jwToken='$jwToken', profilePictureUrl='$profilePictureUrl', accountVerifiedStatus=$accountVerifiedStatus)"
    }
}
