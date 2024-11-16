package com.pretnders.account_management.domain

import com.pretnders.shared.errors.ApplicationException
import com.pretnders.shared.errors.ApplicationExceptionsEnum
import com.pretnders.pretnders.domain.ports.out.FindPretndersOut
import com.pretnders.pretnders.domain.ports.out.ChangePretndersOut
import com.pretnders.shared.utils.validators.PasswordUtils.hashWithBCrypt
import com.pretnders.shared.utils.validators.PasswordUtils.verifyPassword
import com.pretnders.shared.utils.generators.AdminCodeGenerator.generateAdminCode
import com.pretnders.shared.utils.mailer.Mailer
import com.pretnders.shared.utils.validators.InputsValidator.hasTimestampExceededTwentyMinutes
import com.pretnders.shared.utils.validators.InputsValidator.validatePasswordConfirmation
import com.pretnders.shared.utils.validators.InputsValidator.validatePasswordFormat
import com.pretnders.shared.utils.validators.InputsValidator.validatePasswordHash
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import io.quarkus.logging.Log
import java.sql.Timestamp
import java.time.Instant

@ApplicationScoped
class PasswordManagement : PasswordManagementIn {

    @Inject
    @field:Default
    private lateinit var mailer: Mailer

    @Inject
    @field:Default
    private lateinit var changePretndersOut: ChangePretndersOut

    @Inject
    @field:Default
    private lateinit var findPretndersOut: FindPretndersOut

    override fun initPasswordRecovery(identifier: String) {
        val user = findPretndersOut.findByIdentifier(identifier)
        val mail = user.mail
        val token = generateAdminCode()
        Log.info(token)
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