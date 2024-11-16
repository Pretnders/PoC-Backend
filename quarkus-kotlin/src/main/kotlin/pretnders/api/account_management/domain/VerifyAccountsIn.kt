package pretnders.api.account_management.domain

interface VerifyAccountsIn {
    fun verifyPretnderAccount(mail:String, otp:String)
    fun generateNewOtpCode(mail:String)

}