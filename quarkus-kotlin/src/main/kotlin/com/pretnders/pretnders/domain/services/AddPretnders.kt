package com.pretnders.pretnders.domain.services

import com.pretnders.shared.errors.ApplicationException
import com.pretnders.shared.errors.ApplicationExceptionsEnum
import com.pretnders.shared.utils.shared_models.UserTypes
import com.pretnders.pretnders.domain.models.CreatePretenderCommand
import com.pretnders.shared.utils.shared_models.UserBasicInformations
import com.pretnders.azure.domain.ports.out.StorageClientOut
import com.pretnders.azure.domain.ports.out.SecretsClientOut
import com.pretnders.shared.security.JwtTokenGenerator
import com.pretnders.shared.utils.mailer.Mailer
import com.pretnders.shared.utils.validators.PasswordUtils.hashWithBCrypt
import com.pretnders.pretnders.domain.ports.`in`.AddPretndersIn
import com.pretnders.pretnders.domain.ports.out.AddPretndersOut
import com.pretnders.shared.utils.validators.InputsValidator.validatePasswordFormat
import com.pretnders.shared.utils.validators.InputsValidator.validatePhoneNumberFormat
import com.pretnders.shared.utils.generators.OtpGenerator
import com.pretnders.shared.utils.generators.UUIDGenerator.getNewUUID
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import java.sql.Timestamp

@ApplicationScoped
class AddPretnders : AddPretndersIn {

    @Inject
    @field:Default
    private lateinit var jwtTokenGenerator: JwtTokenGenerator
    @Inject
    @field:Default
    private lateinit var mailer: Mailer
    @Inject
    @field:Default
    private lateinit var addPretndersOut: AddPretndersOut
    @Inject
    @field:Default
    private lateinit var storageClientOut: StorageClientOut
    @Inject
    @field:Default
    private lateinit var secretsClientOut: SecretsClientOut

    override fun createPretender(user: CreatePretenderCommand): UserBasicInformations {
        Log.info("Creating user")
        val userType = UserTypes.PRETNDER.name
        val userReference = setUpUserDataAndCheckInputs(user)
        val userToken = jwtTokenGenerator.getToken(userReference, user.phoneNumber, user.mail,userType)
        addPretndersOut.addPretender(user)
        storageClientOut.createContainerForPretnder(user.phoneNumber)
        Log.info("OTP verification Mail sent to user")
       return UserBasicInformations(userReference, userToken, false)
    }




    fun setUpUserDataAndCheckInputs(
        user: CreatePretenderCommand
    ): String {
        val userReference = getNewUUID()
        val preHashPW = user.password
        val verificationCode = OtpGenerator.generateCode()
        val content = mailer.generateOtpEmail(user.firstName, verificationCode)
        user.reference = userReference
        mailer.sendHtmlEmail(user.mail, "Vérification de compte", content)
        verifyCreateUserInputs(preHashPW, user)
        val hash = hashWithBCrypt(preHashPW)
        user.verificationCode = hashWithBCrypt( verificationCode).result
        user.verificationCodeTimestamp = Timestamp(System.currentTimeMillis())
        user.password = hash.result
        return userReference
    }

    fun verifyCreateUserInputs(preHashPW: String, user: CreatePretenderCommand){
        validatePasswordFormat(preHashPW)
        if(user.lastName.length < 3 || user.firstName.length < 2 ) {
            throw ApplicationException(ApplicationExceptionsEnum.CREATE_USER_INVALID_NAME)
        }
       validatePhoneNumberFormat(user.phoneNumber)
    }
}