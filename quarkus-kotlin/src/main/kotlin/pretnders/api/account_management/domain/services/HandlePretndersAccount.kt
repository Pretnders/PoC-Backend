package pretnders.api.account_management.domain.services

import pretnders.api.shared.errors.ApplicationException
import pretnders.api.shared.errors.ApplicationExceptionsEnum
import pretnders.api.pretnders.domain.ports.out.QueryPretndersOut
import pretnders.api.account_management.domain.ports.out.CommandPretndersAccountsOut
import pretnders.api.shared.utils.validators.PasswordUtils.hashWithBCrypt
import pretnders.api.shared.utils.validators.PasswordUtils.verifyPassword
import pretnders.api.shared.utils.validators.InputsValidator.hasTimestampExceededTwentyMinutes
import pretnders.api.shared.utils.generators.OtpGenerator.generateCode
import pretnders.api.shared.utils.mailer.Mailer
import jakarta.enterprise.context.ApplicationScoped
import pretnders.api.account_management.domain.ports.`in`.HandlePretndersAccountsIn
import java.sql.Timestamp
import java.time.Instant


@ApplicationScoped
class HandlePretndersAccount (
    private val queryPretndersOut: QueryPretndersOut,
    private val commandPretndersAccountsOut: CommandPretndersAccountsOut,
    private val mailer: Mailer
): HandlePretndersAccountsIn {

    override fun verifyPretnderAccount(mail: String, otp: String) {
        val user = queryPretndersOut.findByIdentifier(mail)
        val otpTimestamp = user.verificationCodeTimestamp!!
        if(verifyPassword(otp, user.verificationCode!!)){
            hasTimestampExceededTwentyMinutes(otpTimestamp, Timestamp.from(Instant.now()))
            commandPretndersAccountsOut.approveAccount(mail)
        } else {
            throw  ApplicationException(ApplicationExceptionsEnum.OTP_CODES_NO_MATCH)
        }
    }

    override fun generateNewOtpCode(mail: String) {
        val newCode = generateCode()
        val hashedOtp = hashWithBCrypt(newCode).result
        val newTimestamp = Timestamp.from(Instant.now())
        val content = mailer.newOtpEmail(newCode)
        mailer.sendHtmlEmail(mail, "Mise à jour du code OTP", content)
        commandPretndersAccountsOut.changeOtpCode(mail, hashedOtp, newTimestamp)
    }

}