package pretnders.api.pretnders.domain.services

import pretnders.api.shared.errors.ApplicationException
import pretnders.api.shared.errors.ApplicationExceptionsEnum
import pretnders.api.shared.utils.shared_models.UserTypes
import pretnders.api.pretnders.domain.models.CreatePretenderCommand
import pretnders.api.shared.utils.shared_models.UserBasicInformations
import pretnders.api.azure.domain.ports.out.StorageClientOut
import pretnders.api.azure.domain.ports.out.SecretsClientOut
import pretnders.api.shared.security.JwtTokenGenerator
import pretnders.api.shared.utils.mailer.Mailer
import pretnders.api.shared.utils.validators.PasswordUtils.hashWithBCrypt
import pretnders.api.pretnders.domain.ports.`in`.AddPretndersIn
import pretnders.api.pretnders.domain.ports.out.AddPretndersOut
import pretnders.api.shared.utils.validators.InputsValidator.validatePasswordFormat
import pretnders.api.shared.utils.validators.InputsValidator.validatePhoneNumberFormat
import pretnders.api.shared.utils.generators.OtpGenerator
import pretnders.api.shared.utils.generators.UUIDGenerator.getNewUUID
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