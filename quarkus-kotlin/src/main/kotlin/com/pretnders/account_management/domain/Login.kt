package com.pretnders.account_management.domain

import com.pretnders.admins.domain.AdminLoggedIn
import com.pretnders.admins.domain.AdminMapper
import com.pretnders.pretnders.domain.mappers.PretndersMappers
import com.pretnders.pretnders.domain.models.PretnderLoggedIn
import com.pretnders.shared.errors.ApplicationException
import com.pretnders.shared.errors.ApplicationExceptionsEnum
import com.pretnders.shared.security.JwtTokenGenerator
import com.pretnders.shared.utils.shared_models.UserTypes
import com.pretnders.shared.utils.validators.PasswordUtils.verifyPassword
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject

@ApplicationScoped
class Login(@field:Inject var jwtTokenGenerator: JwtTokenGenerator) : LoginIn {

    @Inject
    @field:Default
    private lateinit var logInOut: LogInOut

    @Inject
    @field:Default
    private lateinit var pretndersMappers: PretndersMappers

    @Inject
    @field:Default
    private lateinit var adminMapper: AdminMapper

    override fun loginAdmin(identifier: String, password: String): AdminLoggedIn {
        val admin = logInOut.findAdminByIdentifier(identifier)
        if(verifyPassword(password, admin.password)) {
            Log.info("Admin login successful")
            val jwToken = jwtTokenGenerator.getToken(admin.reference, admin.phoneNumber, admin.mail, UserTypes.ADMIN
                .name)
            return adminMapper.fromAdminToAdminLoggedIn(admin, jwToken)
        } else {
            throw ApplicationException(ApplicationExceptionsEnum.ERROR_VALIDATING_PASSWORD_HASH)
        }
    }

    override fun loginPretnder(identifier: String, password: String): PretnderLoggedIn {
        val user = logInOut.findPretnderByIdentifier(identifier)
        if(verifyPassword(password, user.password)) {
            Log.info("Pretnder login successful")
            val jwToken = jwtTokenGenerator.getToken(user.reference, user.phoneNumber, user.mail, UserTypes.PRETNDER
                .name)
            return pretndersMappers.fromUsersToUsersLoggedIn(user, jwToken)
        } else {
            throw ApplicationException(ApplicationExceptionsEnum.ERROR_VALIDATING_PASSWORD_HASH)
        }
    }
}