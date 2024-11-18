package pretnders.api.account_management.domain

import pretnders.api.shared.errors.ApplicationException
import pretnders.api.shared.errors.ApplicationExceptionsEnum
import pretnders.api.pretnders.domain.ports.out.FindPretndersOut
import pretnders.api.pretnders.domain.ports.out.ChangePretndersOut
import pretnders.api.shared.utils.validators.PasswordUtils.hashWithBCrypt
import pretnders.api.shared.utils.validators.PasswordUtils.verifyPassword
import pretnders.api.shared.utils.validators.InputsValidator.hasTimestampExceededTwentyMinutes
import pretnders.api.shared.utils.generators.OtpGenerator.generateCode
import pretnders.api.shared.utils.mailer.Mailer
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import java.sql.Timestamp
import java.time.Instant


@ApplicationScoped
class VerifyAccounts (
    private val findPretndersOut: FindPretndersOut,
    private val changePretndersOut: ChangePretndersOut,
    private val mailer: Mailer
): VerifyAccountsIn {

    override fun verifyPretnderAccount(mail: String, otp: String) {
        val user = findPretndersOut.findByIdentifier(mail)
        val otpTimestamp = user.verificationCodeTimestamp!!
        if(verifyPassword(otp, user.verificationCode!!)){
            hasTimestampExceededTwentyMinutes(otpTimestamp, Timestamp.from(Instant.now()))
            changePretndersOut.approveAccount(mail)
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
        changePretndersOut.changeOtpCode(mail, hashedOtp, newTimestamp)
    }

}