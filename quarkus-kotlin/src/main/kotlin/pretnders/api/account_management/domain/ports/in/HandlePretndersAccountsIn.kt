package pretnders.api.account_management.domain.ports.`in`

interface HandlePretndersAccountsIn {
    fun verifyPretnderAccount(mail:String, otp:String)
    fun generateNewOtpCode(mail:String)
}