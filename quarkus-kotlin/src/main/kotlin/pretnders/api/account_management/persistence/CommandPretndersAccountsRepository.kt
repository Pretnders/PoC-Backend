package pretnders.api.account_management.persistence

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import pretnders.api.pretnders.persistence.entities.PretndersEntity
import java.sql.Timestamp


@ApplicationScoped
@Transactional
class CommandPretndersAccountsRepository:PanacheRepository<PretndersEntity> {
    fun approveAccount(mail: String) {
        update("accountVerifiedStatus = true WHERE mail =:mail",  mapOf(
            "mail" to mail
        ))
        Log.debug(String.format("User %s was approved", mail))
    }

    fun changeOtpCode(mail: String, newOtp: String, newOtpTimestamp: Timestamp) {
        update("verificationCode = :newOtp, verificationCodeTimestamp = :newTimestamp WHERE " +
                "mail" +
                " =:mail",
            mapOf(
                "newOtp" to newOtp,
                "newTimestamp" to newOtpTimestamp,
                "mail" to mail
            ))
        Log.debug(String.format("User %s verification code updated", mail))

    }

    fun initPasswordRecovery(
        identifier: String,
        passwordVerificationCode: String,
        passwordVerificationTimestamp: Timestamp
    ) {
        update("passwordVerificationCode = :verificationCode, passwordVerificationTimestamp" +
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
    }

    fun changePassword(identifier: String, newPassword: String) {
        update("password = :newPassword WHERE mail =:identifier OR phoneNumber = :identifier",
            mapOf(
                "newPassword" to newPassword,
                "identifier" to identifier
            ))
        Log.debug(String.format("User %s verification code updated", identifier))
    }
}