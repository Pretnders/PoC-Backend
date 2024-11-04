package com.pretnders.persistence.services.users

import com.pretnders.domain.errors.ApplicationException
import com.pretnders.domain.errors.ApplicationExceptionsEnum
import com.pretnders.domain.models.users.User
import com.pretnders.domain.ports.out.FindPretendersOut
import com.pretnders.persistence.mappers.users.UsersEntityMapper
import com.pretnders.persistence.repositories.PretendersRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject

@ApplicationScoped
class FindPretendersSpi:FindPretendersOut {

    @Inject
    @field:Default
    private lateinit var pretendersRepository: PretendersRepository

    @Inject
    @field:Default
    private lateinit var usersEntityMapper: UsersEntityMapper

    override fun findByIdentifier(identifier: String): User {
        val userInDb = pretendersRepository.findByIdentifier(identifier).orElseThrow { ApplicationException(
            ApplicationExceptionsEnum.LOGIN_USER_NOT_FOUND) }
        val user = usersEntityMapper.fromEntityToUser(userInDb)
        return user
    }
}