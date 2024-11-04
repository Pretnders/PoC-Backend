package com.templates.domain.ports.`in`

interface VerifyAccountsIn {
    fun verifyClientAccount(mail:String, otp:String)
    fun generateNewOtpCode(mail:String)

}