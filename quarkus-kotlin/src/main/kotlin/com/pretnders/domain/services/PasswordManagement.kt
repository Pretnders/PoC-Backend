package com.pretnders.domain.services

import com.pretnders.domain.ports.`in`.PasswordManagementIn
import com.pretnders.domain.ports.out.FindClientsOut
import com.pretnders.domain.ports.out.FindPretendersOut
import com.pretnders.domain.ports.out.UpdatePretendersOut
import com.pretnders.domain.services.PasswordUtils.hashWithBCrypt
import com.pretnders.domain.utils.AdminCodeGenerator.generateAdminCode
import com.pretnders.domain.utils.InputsValidator.hasTimestampExceededTwentyMinutes
import com.pretnders.domain.utils.InputsValidator.validatePasswordConfirmation
import com.pretnders.domain.utils.InputsValidator.validatePasswordFormat
import com.pretnders.domain.utils.InputsValidator.validatePasswordHash
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import io.quarkus.logging.Log;
import java.sql.Timestamp
import java.time.Instant

@ApplicationScoped
class PasswordManagement : PasswordManagementIn {

    @Inject
    @field:Default
    private lateinit var mailer: Mailer

    @Inject
    @field:Default
    private lateinit var updatePretendersOut: UpdatePretendersOut

    @Inject
    @field:Default
    private lateinit var findPretendersOut: FindPretendersOut

    override fun initPasswordRecovery(identifier: String) {
        val user = findPretendersOut.findByIdentifier(identifier)
        val mail = user.mail
        val token = generateAdminCode()
        Log.info(token)
        val mailContent = mailer.generatePasswordRecoveryEmail(token)
        val safeToken = hashWithBCrypt(token).result
        val tokenTimestamp = Timestamp.from(Instant.now())
        mailer.sendHtmlEmail(mail, "Récupération de mot de passe", mailContent)
        updatePretendersOut.initPasswordRecovery(mail, safeToken, tokenTimestamp)
    }

    override fun recoverPassword( mail:String,token: String, password: String, passwordConfirmation: String) {
        val user = findPretendersOut.findByIdentifier(mail)
        val hashedToken = user.passwordVerificationCode!!
        val currentTimestamp = user.passwordVerificationTimestamp!!
        validatePasswordFormat(password)
        validatePasswordConfirmation(password, passwordConfirmation)
        validatePasswordHash(token, hashedToken)
        hasTimestampExceededTwentyMinutes(currentTimestamp, Timestamp.from(Instant.now()))
        val hashedPw = hashWithBCrypt(password).result
        updatePretendersOut.changePassword(user.mail, hashedPw)
    }


    override fun changePassword(mail: String, password: String, passwordConfirmation: String) {
        validatePasswordFormat(password)
        validatePasswordConfirmation(password, passwordConfirmation)
        val hashedPw = hashWithBCrypt(password).result
        updatePretendersOut.changePassword(mail, hashedPw)
    }


}