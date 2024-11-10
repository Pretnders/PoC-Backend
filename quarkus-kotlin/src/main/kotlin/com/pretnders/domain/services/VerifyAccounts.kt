package com.pretnders.domain.services

import com.pretnders.domain.errors.ApplicationException
import com.pretnders.domain.errors.ApplicationExceptionsEnum
import com.pretnders.domain.ports.`in`.VerifyAccountsIn
import com.pretnders.domain.ports.out.FindPretendersOut
import com.pretnders.domain.ports.out.UpdatePretndersOut
import com.pretnders.domain.services.PasswordUtils.hashWithBCrypt
import com.pretnders.domain.services.PasswordUtils.verifyPassword
import com.pretnders.domain.utils.InputsValidator.hasTimestampExceededTwentyMinutes
import com.pretnders.domain.utils.OtpGenerator.generateCode
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import java.sql.Timestamp
import java.time.Instant


@ApplicationScoped
class VerifyAccounts:VerifyAccountsIn {
    @Inject
    @field:Default
    private lateinit var findPretendersOut: FindPretendersOut

    @Inject
    @field:Default
    private lateinit var updatePretndersOut: UpdatePretndersOut

    @Inject
    @field:Default
    private lateinit var mailer: Mailer


    override fun verifyClientAccount(mail: String, otp: String) {
        val user = findPretendersOut.findByIdentifier(mail)
        val otpTimestamp = user.verificationCodeTimestamp!!
        if(verifyPassword(otp, user.verificationCode!!)){
            hasTimestampExceededTwentyMinutes(otpTimestamp, Timestamp.from(Instant.now()))
            updatePretndersOut.approveAccount(mail)
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
        updatePretndersOut.changeOtpCode(mail, hashedOtp, newTimestamp)
    }

}