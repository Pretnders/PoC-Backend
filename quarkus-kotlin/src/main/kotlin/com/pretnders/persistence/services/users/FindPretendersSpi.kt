package com.pretnders.persistence.services.users

import com.pretnders.domain.errors.ApplicationException
import com.pretnders.domain.errors.ApplicationExceptionsEnum
import com.pretnders.domain.models.users.Pretnder
import com.pretnders.domain.ports.out.FindPretendersOut
import com.pretnders.persistence.mappers.users.PretndersEntityMapper
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
    private lateinit var pretndersEntityMapper: PretndersEntityMapper

    override fun findByIdentifier(identifier: String): Pretnder {
        val userInDb = pretendersRepository.findByIdentifier(identifier).orElseThrow { ApplicationException(
            ApplicationExceptionsEnum.LOGIN_USER_NOT_FOUND) }
        val user = pretndersEntityMapper.fromEntityToPretnder(userInDb)
        return user
    }
}