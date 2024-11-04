package com.templates.persistence.services.users

import com.templates.domain.errors.ApplicationException
import com.templates.domain.errors.ApplicationExceptionsEnum
import com.templates.domain.models.users.User
import com.templates.domain.ports.out.FindUserOut
import com.templates.domain.services.PasswordManagement
import com.templates.persistence.mappers.users.UsersEntityMapper
import com.templates.persistence.repositories.UsersRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import org.jboss.logging.Logger

@ApplicationScoped
class FindUsersSpi:FindUserOut {

    @Inject
    @field:Default
    private lateinit var usersRepository: UsersRepository

    @Inject
    @field:Default
    private lateinit var usersEntityMapper: UsersEntityMapper

    override fun findByIdentifier(identifier: String): User {
        val userInDb = usersRepository.findByIdentifier(identifier).orElseThrow { ApplicationException(
            ApplicationExceptionsEnum.LOGIN_USER_NOT_FOUND) }
        val user = usersEntityMapper.fromEntityToUser(userInDb)
        return user
    }
}