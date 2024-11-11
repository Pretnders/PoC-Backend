package com.pretnders.domain.models.pretnders

import java.sql.Timestamp

class Pretnder(
    val nickname:String,
    val firstName: String,
    val lastName: String,
    val mail: String,
    val password: String,
    val phoneNumber: String,
    val reference: String,
    var verificationCode: String? = null,
    var verificationCodeTimestamp: Timestamp? = null,
    var accountVerifiedStatus: Boolean? = false,
    var passwordVerificationCode: String? = null,
    var passwordVerificationTimestamp: Timestamp? = null,
    val pretnderDetails: PretnderDetails? = null
){
    override fun toString(): String {
        return "Pretnder(nickname='$nickname', firstName='$firstName', lastName='$lastName', mail='$mail', password='$password', phoneNumber='$phoneNumber', reference='$reference', verificationCode=$verificationCode, verificationCodeTimestamp=$verificationCodeTimestamp, accountVerifiedStatus=$accountVerifiedStatus, passwordVerificationCode=$passwordVerificationCode, passwordVerificationTimestamp=$passwordVerificationTimestamp, pretnderDetails=$pretnderDetails)"
    }
}
