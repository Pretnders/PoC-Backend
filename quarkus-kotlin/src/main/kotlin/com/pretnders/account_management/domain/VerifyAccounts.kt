package com.pretnders.account_management.domain

import com.pretnders.shared.errors.ApplicationException
import com.pretnders.shared.errors.ApplicationExceptionsEnum
import com.pretnders.pretnders.domain.ports.out.FindPretndersOut
import com.pretnders.pretnders.domain.ports.out.ChangePretndersOut
import com.pretnders.shared.utils.validators.PasswordUtils.hashWithBCrypt
import com.pretnders.shared.utils.validators.PasswordUtils.verifyPassword
import com.pretnders.shared.utils.validators.InputsValidator.hasTimestampExceededTwentyMinutes
import com.pretnders.shared.utils.generators.OtpGenerator.generateCode
import com.pretnders.shared.utils.mailer.Mailer
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import java.sql.Timestamp
import java.time.Instant


@ApplicationScoped
class VerifyAccounts: VerifyAccountsIn {
    @Inject
    @field:Default
    private lateinit var findPretndersOut: FindPretndersOut

    @Inject
    @field:Default
    private lateinit var changePretndersOut: ChangePretndersOut

    @Inject
    @field:Default
    private lateinit var mailer: Mailer


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