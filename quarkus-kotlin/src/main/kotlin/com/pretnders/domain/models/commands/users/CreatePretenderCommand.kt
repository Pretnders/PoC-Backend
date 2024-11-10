package com.pretnders.domain.models.commands.users

import java.sql.Timestamp

class CreatePretenderCommand(
    val nickname: String,
    val firstName: String,
    val lastName: String,
    val mail: String,
    var password: String,
    val phoneNumber: String,
    var reference: String? = null,
    var verificationCode: String ? = null,
    var verificationCodeTimestamp:  Timestamp? = null,
    var accountVerified: Boolean = false,
) {
    override fun toString(): String {
        return "CreatePretenderCommand(nickname='$nickname', firstName='$firstName', lastName='$lastName', mail='$mail', password='$password', phoneNumber='$phoneNumber', reference=$reference, verificationCode=$verificationCode, verificationCodeTimestamp=$verificationCodeTimestamp, accountVerified=$accountVerified)"
    }
}
