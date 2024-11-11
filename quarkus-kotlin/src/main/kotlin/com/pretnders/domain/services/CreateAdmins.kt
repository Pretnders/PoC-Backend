package com.pretnders.domain.services

import com.pretnders.domain.errors.ApplicationException
import com.pretnders.domain.errors.ApplicationExceptionsEnum
import com.pretnders.domain.models.UserTypes
import com.pretnders.domain.models.admins.CreateAdminCommand
import com.pretnders.domain.models.pretnders.UserBasicInformations
import com.pretnders.domain.ports.`in`.AzureStorageIn
import com.pretnders.domain.ports.`in`.CreateAdminIn
import com.pretnders.domain.ports.out.CreateAdminOut
import com.pretnders.domain.ports.out.SecretsClientOut
import com.pretnders.domain.services.PasswordUtils.hashWithBCrypt
import com.pretnders.domain.utils.AdminCodeGenerator.generateAdminCode
import com.pretnders.domain.utils.InputsValidator
import com.pretnders.domain.utils.UUIDGenerator.getNewUUID
import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject

@RequestScoped
class CreateAdmins:CreateAdminIn {
    @Inject
    private lateinit var secretsClientOut: SecretsClientOut


    @Inject
    private lateinit var azureStorageIn: AzureStorageIn
    @Inject
    private lateinit var createAdminOut: CreateAdminOut

    @Inject
    private lateinit var jwtTokenGenerator: JwtTokenGenerator

    @Inject
    private lateinit var mailer: Mailer

    override fun createAdmin(createAdminCommand: CreateAdminCommand): UserBasicInformations {
        InputsValidator.validatePasswordFormat(createAdminCommand.password)
        InputsValidator.validatePhoneNumberFormat(createAdminCommand.phoneNumber)
        if(createAdminCommand.adminCode == secretsClientOut.getCurrentAdminCreationCode() ){
            val reference = getNewUUID()
            secretsClientOut.updateAdminCode(generateAdminCode())
            val jwToken = jwtTokenGenerator.getToken(reference, createAdminCommand.phoneNumber, createAdminCommand
                .mail, UserTypes
                .ADMIN.name)
            createAdminCommand.reference = reference
            createAdminCommand.password = hashWithBCrypt(createAdminCommand.password).result
            mailer.sendHtmlEmail(createAdminCommand.mail, "Welcome",mailer.generateOtpEmail(createAdminCommand
                .nickname, ""))
            createAdminOut.addAdmin(createAdminCommand)
            azureStorageIn.createContainerForAdmin(createAdminCommand.phoneNumber)
            return UserBasicInformations(
                UserTypes.ADMIN.name,
                reference,
                jwToken,
                true
            )
        } else {
            throw ApplicationException(ApplicationExceptionsEnum.ADMIN_VERIFICATION_CODE_NO_MATCH)
        }
    }
}