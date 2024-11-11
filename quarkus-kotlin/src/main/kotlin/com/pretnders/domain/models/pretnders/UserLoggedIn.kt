package com.pretnders.domain.models.pretnders

data class UserLoggedIn(
    val nickname:String,
    val firstName: String,
    val lastName: String,
    val mail: String,
    val phoneNumber: String,
    val reference: String,
    val jwToken:String,
    val accountVerifiedStatus:Boolean
){
    override fun toString(): String {
        return "UserLoggedIn(nickname='$nickname', firstName='$firstName', lastName='$lastName', mail='$mail', phoneNumber='$phoneNumber', reference='$reference', jwToken='$jwToken', accountVerifiedStatus=$accountVerifiedStatus)"
    }
}
