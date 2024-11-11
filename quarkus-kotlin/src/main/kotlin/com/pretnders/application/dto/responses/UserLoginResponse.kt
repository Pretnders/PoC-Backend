package com.pretnders.application.dto.responses

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginResponse(
    val nickname:String,
    val firstName: String,
    val lastName: String,
    val accountVerifiedStatus: Boolean,
){
    override fun toString(): String {
        return "UserLoginResponse(nickname='$nickname', firstName='$firstName', lastName='$lastName', accountVerifiedStatus=$accountVerifiedStatus)"
    }
}
