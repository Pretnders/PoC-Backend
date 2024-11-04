package com.templates.persistence.services.users

import com.templates.domain.errors.ApplicationException
import com.templates.domain.errors.ApplicationExceptionsEnum
import com.templates.domain.models.users.User
import com.templates.domain.ports.out.FindClientsOut
import com.templates.persistence.entities.ClientsEntity
import com.templates.persistence.mappers.users.UsersEntityMapper
import com.templates.persistence.repositories.ClientsRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@ApplicationScoped
class FindClientsSpi:FindClientsOut {

    @Inject
    @field:Default
    private lateinit var clientsRepository: ClientsRepository

    @Inject
    @field:Default
    private lateinit var usersEntityMapper: UsersEntityMapper

    override fun findByIdentifier(identifier: String): User {
        val clientFromDb = clientsRepository.findByIdentifier(identifier).orElseThrow { ApplicationException(ApplicationExceptionsEnum.LOGIN_USER_NOT_FOUND) }
        val user = usersEntityMapper.fromClientToUser(clientFromDb)
        return user
    }

    override fun findByPasswordVerificationCode(passwordVerificationCode: String): User {
        return usersEntityMapper.fromClientToUser(clientsRepository.find("passwordVerificationCode", passwordVerificationCode)
            .firstResult<ClientsEntity>())
    }
}