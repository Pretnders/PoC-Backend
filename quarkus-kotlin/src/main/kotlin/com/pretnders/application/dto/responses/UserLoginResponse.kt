package com.pretnders.application.dto.responses

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginResponse(
    val firstName: String,
    val lastName: String,
    val mail: String,
    val phoneNumber: String,
    val reference: String,
    val type: String,
    val accountVerifiedStatus: Boolean,
){
    override fun toString(): String {
        return "UserLoginResponse(firstName='$firstName', lastName='$lastName', mail='$mail', phoneNumber='$phoneNumber', reference='$reference', type='$type', accountVerifiedStatus=$accountVerifiedStatus)"
    }
}
