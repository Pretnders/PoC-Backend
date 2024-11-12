package com.pretnders.domain.services

import com.pretnders.domain.errors.ApplicationException
import com.pretnders.domain.errors.ApplicationExceptionsEnum
import com.pretnders.domain.mappers.AdminMapper
import com.pretnders.domain.mappers.PretndersMappers
import com.pretnders.domain.models.UserTypes
import com.pretnders.domain.models.admins.AdminLoggedIn
import com.pretnders.domain.models.pretnders.PretnderLoggedIn
import com.pretnders.domain.ports.`in`.LoginIn
import com.pretnders.domain.ports.out.FindAdminsOut
import com.pretnders.domain.ports.out.FindPretendersOut
import com.pretnders.domain.services.PasswordUtils.verifyPassword
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import io.quarkus.logging.Log

@ApplicationScoped
class Login(@field:Inject var jwtTokenGenerator: JwtTokenGenerator) : LoginIn {

    @Inject
    @field:Default
    private lateinit var findPretendersOut: FindPretendersOut
    @Inject
    @field:Default
    private lateinit var findAdminsOut: FindAdminsOut

    @Inject
    @field:Default
    private lateinit var pretndersMappers: PretndersMappers

    @Inject
    @field:Default
    private lateinit var adminMapper: AdminMapper

    override fun loginAdmin(identifier: String, password: String): AdminLoggedIn {
        val admin = findAdminsOut.findByIdentifier(identifier)
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
        val user = findPretendersOut.findByIdentifier(identifier)
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