package com.templates.domain.services

import com.templates.domain.errors.ApplicationException
import com.templates.domain.errors.ApplicationExceptionsEnum
import com.templates.domain.mappers.UsersMappers
import com.templates.domain.models.users.UserLoggedIn
import com.templates.domain.ports.`in`.LoginIn
import com.templates.domain.ports.out.FindUserOut
import com.templates.domain.services.PasswordUtils.verifyPassword
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import io.quarkus.logging.Log;

@ApplicationScoped
class Login(@field:Inject var jwtTokenGenerator: JwtTokenGenerator) : LoginIn {

    @Inject
    @field:Default
    private lateinit var findUserOut: FindUserOut


    @Inject
    @field:Default
    private lateinit var usersMappers: UsersMappers


    override fun login(identifier: String, password: String): UserLoggedIn {
        val user = findUserOut.findByIdentifier(identifier)
        Log.info(user.toString())
        if(verifyPassword(password, user.password)) {
            Log.info("Login successful")
            val jwToken = jwtTokenGenerator.getToken(user.mail, user.type)
            return usersMappers.fromUsersToUsersLoggedIn(user, jwToken)
        } else {
            throw ApplicationException(ApplicationExceptionsEnum.ERROR_VALIDATING_PASSWORD_HASH)
        }
    }
}