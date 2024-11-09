package com.pretnders.persistence.services.users

import com.pretnders.domain.errors.ApplicationException
import com.pretnders.domain.errors.ApplicationExceptionsEnum
import com.pretnders.domain.ports.out.UpdateAdminsOut
import com.pretnders.persistence.repositories.AdminsRepository
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

    override fun updateNickname(mail: String, newNickname: String) {
        try {
            adminsRepository.update("nickname = :newNickname WHERE mail = :mail",
                mapOf(
                "mail" to mail,
                "newNickname" to newNickname
            ))
            Log.debug(String.format("Admin %s nickname was updated", mail))
        } catch (e: SQLException) {
            handleExceptions(e)
        }

    }
    override fun updateProfilePicture(phoneNumber: String, profilePictureUrl:String) {
        try {
            adminsRepository.update("profilePicture = :profilePictureUrl WHERE phone = :phoneNumber",
                mapOf(
                    "phoneNumber" to phoneNumber,
                    "profilePictureUrl" to profilePictureUrl
                ))
            Log.debug(String.format("User %s profile picture was updated", phoneNumber))
        } catch (e: SQLException) {
            handleExceptions(e)
        }
    }

    private fun handleExceptions(e: SQLException) {
        Log.debug(e.message)
        throw ApplicationException(ApplicationExceptionsEnum.ERROR)
    }

}