package com.pretnders.pretnders.domain.models

import com.pretnders.profile_pictures.domain.models.ProfilePicture
import java.sql.Timestamp

class Pretnder(
    val nickname:String,
    val firstName: String,
    val lastName: String,
    val mail: String,
    val device: String,
    val password: String,
    val phoneNumber: String,
    val reference: String,
    var verificationCode: String? = null,
    var verificationCodeTimestamp: Timestamp? = null,
    var accountVerifiedStatus: Boolean? = false,
    var passwordVerificationCode: String? = null,
    var passwordVerificationTimestamp: Timestamp? = null,
    val pretnderDetails: PretnderDetails? = null,
    var profilePictures: List<ProfilePicture>? = ArrayList(),
    var traitPairs: List<PretnderTraitPair>? = ArrayList()
){
    override fun toString(): String {
        return "Pretnder(nickname='$nickname', firstName='$firstName', lastName='$lastName', mail='$mail', device='$device', password='$password', phoneNumber='$phoneNumber', reference='$reference', verificationCode=$verificationCode, verificationCodeTimestamp=$verificationCodeTimestamp, accountVerifiedStatus=$accountVerifiedStatus, passwordVerificationCode=$passwordVerificationCode, passwordVerificationTimestamp=$passwordVerificationTimestamp, pretnderDetails=$pretnderDetails, profilePictures=$profilePictures, traitPairs=$traitPairs)"
    }
}
