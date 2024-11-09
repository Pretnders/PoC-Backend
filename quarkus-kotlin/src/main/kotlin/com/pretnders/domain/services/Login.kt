package com.pretnders.domain.services

import com.pretnders.domain.errors.ApplicationException
import com.pretnders.domain.errors.ApplicationExceptionsEnum
import com.pretnders.domain.mappers.UsersMappers
import com.pretnders.domain.models.users.UserLoggedIn
import com.pretnders.domain.ports.`in`.LoginIn
import com.pretnders.domain.ports.out.FindPretendersOut
import com.pretnders.domain.services.PasswordUtils.verifyPassword
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import io.quarkus.logging.Log;

@ApplicationScoped
class Login(@field:Inject var jwtTokenGenerator: JwtTokenGenerator) : LoginIn {

    @Inject
    @field:Default
    private lateinit var findPretendersOut: FindPretendersOut


    @Inject
    @field:Default
    private lateinit var usersMappers: UsersMappers


    override fun login(identifier: String, password: String): UserLoggedIn {
        val user = findPretendersOut.findByIdentifier(identifier)
        Log.info(user.toString())
        if(verifyPassword(password, user.password)) {
            Log.info("Login successful")
            val jwToken = jwtTokenGenerator.getToken(user.mail, "Pretender")
            return usersMappers.fromUsersToUsersLoggedIn(user, jwToken)
        } else {
            throw ApplicationException(ApplicationExceptionsEnum.ERROR_VALIDATING_PASSWORD_HASH)
        }
    }
}