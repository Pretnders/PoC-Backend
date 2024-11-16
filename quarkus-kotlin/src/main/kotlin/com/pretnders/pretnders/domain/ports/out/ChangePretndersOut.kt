package com.pretnders.pretnders.domain.ports.out

import java.sql.Timestamp


interface ChangePretndersOut {
    fun approveAccount(mail: String)
    fun changeOtpCode(mail: String, newOtp: String, newOtpTimestamp: Timestamp)
    fun initPasswordRecovery(identifier: String, passwordVerificationCode: String, passwordVerificationTimestamp:
    Timestamp)
    fun changePassword(identifier: String, newPassword: String)

}