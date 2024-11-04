package com.templates.persistence.services.users

import com.templates.domain.errors.ApplicationException
import com.templates.domain.errors.ApplicationExceptionsEnum
import com.templates.domain.ports.out.UpdateAdminsOut
import com.templates.persistence.repositories.AdminsRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import io.quarkus.logging.Log;
import java.sql.SQLException
import java.sql.Timestamp

@ApplicationScoped
@Transactional
class UpdateAdminsSpi:UpdateAdminsOut {

    @Inject
    @field:Default
    private lateinit var adminsRepository: AdminsRepository

    override fun updateProfilePicture(mail: String, profilePictureUrl:String) {
        try {
            adminsRepository.update("profilePicture = :profilePictureUrl WHERE mail = :mail",
                mapOf(
                "mail" to mail,
                "profilePictureUrl" to profilePictureUrl
            ))
            Log.debug(String.format("User %s profile picture was updated", mail))
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
            adminsRepository.update("passwordVerificationCode = :verificationCode, passwordVerificationTimestamp" +
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
            adminsRepository.update("password = :newPassword WHERE mail =:mail OR phoneNumber = :mail",
                mapOf(
                    "newPassword" to newPassword,
                    "mail" to identifier
                ))
            Log.debug(String.format("User %s verification code updated", identifier))
        } catch (e: SQLException) {
            handleExceptions(e)
        }
    }

    private fun handleExceptions(e: SQLException) {
        Log.debug(e.message)
        throw ApplicationException(ApplicationExceptionsEnum.ERROR)
    }

}