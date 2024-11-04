package com.templates.persistence.services.users

import com.templates.domain.errors.ApplicationException
import com.templates.domain.errors.ApplicationExceptionsEnum
import com.templates.domain.models.users.User
import com.templates.domain.ports.out.FindAdminsOut
import com.templates.persistence.mappers.users.UsersEntityMapper
import com.templates.persistence.repositories.AdminsRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import io.quarkus.logging.Log

@ApplicationScoped
class FindAdminsSpi : FindAdminsOut{

    @Inject
    @field:Default
    private lateinit var adminsRepository: AdminsRepository

    @Inject
    @field:Default
    private lateinit var usersEntityMapper: UsersEntityMapper

    override fun findByIdentifier(identifier: String): User {
        val clientFromDb = adminsRepository.findByIdentifier(identifier).orElseThrow { ApplicationException(
            ApplicationExceptionsEnum.LOGIN_USER_NOT_FOUND) }
        Log.debug(clientFromDb.toString())
        val user = usersEntityMapper.fromAdminToUser(clientFromDb)
        Log.debug(user.toString())
        return user
    }
}