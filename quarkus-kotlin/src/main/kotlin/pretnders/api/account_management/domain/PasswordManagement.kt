package pretnders.api.account_management.domain

import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import pretnders.api.pretnders.domain.ports.out.ChangePretndersOut
import pretnders.api.pretnders.domain.ports.out.FindPretndersOut
import pretnders.api.shared.errors.ApplicationException
import pretnders.api.shared.errors.ApplicationExceptionsEnum
import pretnders.api.shared.utils.generators.AdminCodeGenerator.generateAdminCode
import pretnders.api.shared.utils.mailer.Mailer
import pretnders.api.shared.utils.validators.InputsValidator.hasTimestampExceededTwentyMinutes
import pretnders.api.shared.utils.validators.InputsValidator.validatePasswordConfirmation
import pretnders.api.shared.utils.validators.InputsValidator.validatePasswordFormat
import pretnders.api.shared.utils.validators.InputsValidator.validatePasswordHash
import pretnders.api.shared.utils.validators.PasswordUtils.hashWithBCrypt
import pretnders.api.shared.utils.validators.PasswordUtils.verifyPassword
import java.sql.Timestamp
import java.time.Instant

@ApplicationScoped
class PasswordManagement (
    private val mailer: Mailer,
    private val changePretndersOut: ChangePretndersOut,
    private val findPretndersOut: FindPretndersOut
) : PasswordManagementIn {

    override fun initPasswordRecovery(identifier: String) {
        val user = findPretndersOut.findByIdentifier(identifier)
        val mail = user.mail
        val token = generateAdminCode()
        val mailContent = mailer.generatePasswordRecoveryEmail(token)
        val safeToken = hashWithBCrypt(token).result
        val tokenTimestamp = Timestamp.from(Instant.now())
        mailer.sendHtmlEmail(mail, "Récupération de mot de passe", mailContent)
        changePretndersOut.initPasswordRecovery(mail, safeToken, tokenTimestamp)
    }

    override fun recoverPassword( mail:String,token: String, password: String, passwordConfirmation: String) {
        val user = findPretndersOut.findByIdentifier(mail)
        val hashedToken = user.passwordVerificationCode!!
        val currentTimestamp = user.passwordVerificationTimestamp!!
        validatePasswordFormat(password)
        validatePasswordConfirmation(password, passwordConfirmation)
        validatePasswordHash(token, hashedToken)
        hasTimestampExceededTwentyMinutes(currentTimestamp, Timestamp.from(Instant.now()))
        val hashedPw = hashWithBCrypt(password).result
        changePretndersOut.changePassword(user.mail, hashedPw)
    }


    override fun changePassword(mail: String, currentPassword:String, password: String, passwordConfirmation: String) {
        val userPassword = findPretndersOut.findByIdentifier(mail).password
        if(verifyPassword(currentPassword, userPassword)){
            validatePasswordFormat(password)
            validatePasswordConfirmation(password, passwordConfirmation)
            val hashedPw = hashWithBCrypt(password).result
            return changePretndersOut.changePassword(mail, hashedPw)
        }
        throw ApplicationException(ApplicationExceptionsEnum.USER_INVALID_PASSWORD)
    }


}