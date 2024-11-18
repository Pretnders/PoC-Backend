package pretnders.api.account_management.domain

import pretnders.api.admins.domain.AdminLoggedIn
import pretnders.api.admins.domain.AdminMapper
import pretnders.api.pretnders.domain.mappers.PretndersMappers
import pretnders.api.pretnders.domain.models.PretnderLoggedIn
import pretnders.api.shared.errors.ApplicationException
import pretnders.api.shared.errors.ApplicationExceptionsEnum
import pretnders.api.shared.security.JwtTokenGenerator
import pretnders.api.shared.utils.shared_models.UserTypes
import pretnders.api.shared.utils.validators.PasswordUtils.verifyPassword
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject

@ApplicationScoped
class Login(private val jwtTokenGenerator: JwtTokenGenerator,
            private val logInOut: LogInOut,
            private val pretndersMappers: PretndersMappers,
            private val adminMapper: AdminMapper

    ) : LoginIn {

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