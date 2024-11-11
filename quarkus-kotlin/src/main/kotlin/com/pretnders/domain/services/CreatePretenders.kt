package com.pretnders.domain.services

import com.pretnders.domain.errors.ApplicationException
import com.pretnders.domain.errors.ApplicationExceptionsEnum
import com.pretnders.domain.models.UserTypes
import com.pretnders.domain.models.commands.users.CreatePretenderCommand
import com.pretnders.domain.models.users.UserBasicInformations
import com.pretnders.domain.ports.`in`.AzureStorageIn
import com.pretnders.domain.ports.`in`.CreatePretendersIn
import com.pretnders.domain.ports.out.CreatePretendersOut
import com.pretnders.domain.ports.out.SecretsClientOut
import com.pretnders.domain.services.PasswordUtils.hashWithBCrypt
import com.pretnders.domain.utils.InputsValidator.validatePasswordFormat
import com.pretnders.domain.utils.InputsValidator.validatePhoneNumberFormat
import com.pretnders.domain.utils.OtpGenerator
import com.pretnders.domain.utils.UUIDGenerator.getNewUUID
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import java.sql.Timestamp

@ApplicationScoped
class CreatePretenders : CreatePretendersIn {

    @Inject
    @field:Default
    private lateinit var jwtTokenGenerator: JwtTokenGenerator
    @Inject
    @field:Default
    private lateinit var mailer: Mailer
    @Inject
    @field:Default
    private lateinit var createPretendersOut: CreatePretendersOut
    @Inject
    @field:Default
    private lateinit var azureStorageIn: AzureStorageIn
    @Inject
    @field:Default
    private lateinit var secretsClientOut: SecretsClientOut

    override fun createPretender(user: CreatePretenderCommand):UserBasicInformations {
        Log.info("Creating user")
        val userType = UserTypes.PRETNDER.name
        val userReference = setUpUserDataAndCheckInputs(user, userType)
        val userToken = jwtTokenGenerator.getToken(userReference, user.phoneNumber, user.mail,userType)
        createPretendersOut.addPretender(user)
        azureStorageIn.createContainerForPretnder(user.phoneNumber)
        Log.info("OTP verification Mail sent to user")
       return UserBasicInformations(userType, userReference, userToken, false)
    }




    fun setUpUserDataAndCheckInputs(
        user: CreatePretenderCommand,
        userType: String
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