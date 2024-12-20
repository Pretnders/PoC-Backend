package pretnders.api.admins.domain

import pretnders.api.shared.errors.ApplicationException
import pretnders.api.shared.errors.ApplicationExceptionsEnum
import pretnders.api.shared.utils.shared_models.UserTypes
import pretnders.api.shared.utils.shared_models.UserBasicInformations
import pretnders.api.azure.domain.ports.out.StorageClientOut
import pretnders.api.azure.domain.ports.out.SecretsClientOut
import pretnders.api.shared.utils.validators.PasswordUtils.hashWithBCrypt
import pretnders.api.shared.utils.generators.AdminCodeGenerator.generateAdminCode
import pretnders.api.shared.security.JwtTokenGenerator
import pretnders.api.shared.utils.validators.InputsValidator
import pretnders.api.shared.utils.mailer.Mailer
import jakarta.enterprise.context.RequestScoped
import pretnders.api.shared.utils.generators.UUIDGenerator

@RequestScoped
class CreateAdmins (
    private val secretsClientOut: SecretsClientOut,
    private val uuidGenerator: UUIDGenerator,
    private val createAdminOut: CreateAdminOut,
    private val storageClientOut: StorageClientOut,
    private val jwtTokenGenerator: JwtTokenGenerator,
    private val mailer: Mailer
): CreateAdminIn {

    override fun createAdmin(createAdminCommand: CreateAdminCommand): UserBasicInformations {
        InputsValidator.validatePasswordFormat(createAdminCommand.password)
        InputsValidator.validatePhoneNumberFormat(createAdminCommand.phoneNumber)
        if(createAdminCommand.adminCode == secretsClientOut.getCurrentAdminCreationCode() ){
            val reference = uuidGenerator.getNewUUID()
            secretsClientOut.updateAdminCode(generateAdminCode())
            val jwToken = jwtTokenGenerator.getToken(reference, createAdminCommand.phoneNumber, createAdminCommand
                .mail, UserTypes
                .ADMIN.name)
            createAdminCommand.reference = reference
            createAdminCommand.password = hashWithBCrypt(createAdminCommand.password).result
            mailer.sendHtmlEmail(createAdminCommand.mail, "Welcome",mailer.generateOtpEmail(createAdminCommand
                .nickname, ""))
            createAdminOut.addAdmin(createAdminCommand)
            storageClientOut.createContainerForAdmin(createAdminCommand.phoneNumber)
            return UserBasicInformations(
                reference,
                jwToken,
                true
            )
        } else {
            throw ApplicationException(ApplicationExceptionsEnum.ADMIN_VERIFICATION_CODE_NO_MATCH)
        }
    }
}