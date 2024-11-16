package com.pretnders.pretnders.domain.models

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CreatePretenderCommand) return false

        if (nickname != other.nickname) return false
        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (mail != other.mail) return false
        if (password != other.password) return false
        if (phoneNumber != other.phoneNumber) return false
        if (reference != other.reference) return false
        if (verificationCode != other.verificationCode) return false
        if (verificationCodeTimestamp != other.verificationCodeTimestamp) return false
        if (accountVerified != other.accountVerified) return false

        return true
    }

    override fun hashCode(): Int {
        var result = nickname.hashCode()
        result = 31 * result + firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + mail.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + phoneNumber.hashCode()
        result = 31 * result + (reference?.hashCode() ?: 0)
        result = 31 * result + (verificationCode?.hashCode() ?: 0)
        result = 31 * result + (verificationCodeTimestamp?.hashCode() ?: 0)
        result = 31 * result + accountVerified.hashCode()
        return result
    }


}
