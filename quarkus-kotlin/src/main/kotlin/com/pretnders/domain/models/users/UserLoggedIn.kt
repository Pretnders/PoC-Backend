package com.pretnders.domain.models.users

class UserLoggedIn(
    val firstName: String,
    val lastName: String,
    val mail: String,
    val phoneNumber: String,
    val reference: String,
    val type: String,
    val jwToken:String,
    val accountVerifiedStatus:Boolean
){
    override fun toString(): String {
        return "UserLoggedIn(firstName='$firstName', lastName='$lastName', mail='$mail', phoneNumber='$phoneNumber', reference='$reference', type='$type', jwToken='$jwToken', accountVerifiedStatus=$accountVerifiedStatus)"
    }
}
