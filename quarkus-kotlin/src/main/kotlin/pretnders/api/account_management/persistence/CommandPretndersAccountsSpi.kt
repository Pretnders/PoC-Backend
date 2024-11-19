package pretnders.api.account_management.persistence

import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import pretnders.api.account_management.domain.ports.out.CommandPretndersAccountsOut
import pretnders.api.shared.errors.ApplicationException
import pretnders.api.shared.errors.ApplicationExceptionsEnum
import java.sql.SQLException
import java.sql.Timestamp

@ApplicationScoped
@Transactional
class CommandPretndersAccountsSpi(
    private val commandPretndersAccountsRepository: CommandPretndersAccountsRepository
) : CommandPretndersAccountsOut {


    override fun approveAccount(mail: String) {
        try {
            commandPretndersAccountsRepository.approveAccount(mail)
        } catch (e: SQLException) {
            handleExceptions(e)
        }
    }

    override fun changeOtpCode(mail: String, newOtp: String, newOtpTimestamp: Timestamp) {
        try {
            commandPretndersAccountsRepository.changeOtpCode(mail, newOtp, newOtpTimestamp)

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
            commandPretndersAccountsRepository.initPasswordRecovery(
                identifier,
                passwordVerificationCode,
                passwordVerificationTimestamp
            )
        } catch (e: SQLException) {
            handleExceptions(e)
        }
    }

    override fun changePassword(identifier: String, newPassword: String) {
        try {
            commandPretndersAccountsRepository.changePassword(identifier, newPassword)
        } catch (e: SQLException) {
            handleExceptions(e)
        }
    }

    private fun handleExceptions(e: SQLException) {
        Log.debug(e.message)
        throw ApplicationException(ApplicationExceptionsEnum.ERROR)
    }

}