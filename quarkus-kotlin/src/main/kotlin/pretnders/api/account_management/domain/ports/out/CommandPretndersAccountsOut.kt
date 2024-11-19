package pretnders.api.account_management.domain.ports.out

import java.sql.Timestamp


interface CommandPretndersAccountsOut {
    fun approveAccount(mail: String)
    fun changeOtpCode(mail: String, newOtp: String, newOtpTimestamp: Timestamp)
    fun initPasswordRecovery(identifier: String, passwordVerificationCode: String, passwordVerificationTimestamp:
    Timestamp)
    fun changePassword(identifier: String, newPassword: String)

}