package com.pretnders.persistence.services.pretnders

import com.pretnders.domain.errors.ApplicationException
import com.pretnders.domain.errors.ApplicationExceptionsEnum
import com.pretnders.domain.ports.out.UpdatePretndersOut
import com.pretnders.persistence.repositories.PretndersRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import io.quarkus.logging.Log
import java.sql.SQLException
import java.sql.Timestamp

@ApplicationScoped
@Transactional
class UpdatePretndersSpi:UpdatePretndersOut {

    @Inject
    @field:Default
    private lateinit var pretndersRepository: PretndersRepository

    override fun approveAccount(mail: String) {
        try {
            pretndersRepository.update("accountVerifiedStatus = true WHERE mail =:mail",  mapOf(
                "mail" to mail
            ))
            Log.debug(String.format("User %s was approved", mail))
        } catch (e: SQLException) {
            handleExceptions(e)
        }
    }

    override fun changeOtpCode(mail: String, newOtp: String, newOtpTimestamp: Timestamp) {
        try {
            pretndersRepository.update("verificationCode = :newOtp, verificationCodeTimestamp = :newTimestamp WHERE " +
                    "mail" +
                    " =:mail",
                mapOf(
                    "newOtp" to newOtp,
                    "newTimestamp" to newOtpTimestamp,
                    "mail" to mail
                ))
            Log.debug(String.format("User %s verification code updated", mail))
        } catch (e: SQLException) {
            handleExceptions(e)
        }

    }

    override fun initPasswordRecovery(
        identifier: String,
        passwordVerificationCode: String,
        passwordVerificationTimestamp: Timestamp
    ) {
        try {
            pretndersRepository.update("passwordVerificationCode = :verificationCode, passwordVerificationTimestamp" +
                    " = " +
                    ":newTimestamp " +
                    "WHERE " +
                    "mail" +
                    " =:mail",
                mapOf(
                    "verificationCode" to passwordVerificationCode,
                    "newTimestamp" to passwordVerificationTimestamp,
                    "mail" to identifier
                ))
            Log.debug(String.format("User %s verification code updated", identifier))
        } catch (e: SQLException) {
            handleExceptions(e)
        }
    }

    override fun changePassword(identifier: String, newPassword: String) {
        try {
            pretndersRepository.update("password = :newPassword WHERE mail =:identifier OR phoneNumber = :identifier",
                mapOf(
                    "newPassword" to newPassword,
                    "identifier" to identifier
                ))
            Log.debug(String.format("User %s verification code updated", identifier))
        } catch (e: SQLException) {
            handleExceptions(e)
        }
    }

    private fun handleExceptions(e: SQLException) {
        Log.info(e.message)
        throw ApplicationException(ApplicationExceptionsEnum.ERROR)
    }

}