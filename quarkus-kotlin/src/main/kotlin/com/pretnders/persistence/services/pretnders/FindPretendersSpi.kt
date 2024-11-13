package com.pretnders.persistence.services.pretnders

import com.pretnders.domain.errors.ApplicationException
import com.pretnders.domain.errors.ApplicationExceptionsEnum
import com.pretnders.domain.models.pretnders.Pretnder
import com.pretnders.domain.ports.out.FindPretendersOut
import com.pretnders.persistence.mappers.pretnders.PretndersEntityMapper
import com.pretnders.persistence.repositories.PretndersRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject

@ApplicationScoped
class FindPretendersSpi:FindPretendersOut {

    //TODO Unit test this shit

    @Inject
    @field:Default
    private lateinit var pretndersRepository: PretndersRepository

    @Inject
    @field:Default
    private lateinit var pretndersEntityMapper: PretndersEntityMapper

    override fun findByIdentifier(identifier: String): Pretnder {
        val userInDb = pretndersRepository.findByIdentifier(identifier).orElseThrow { ApplicationException(
            ApplicationExceptionsEnum.LOGIN_USER_NOT_FOUND) }
        return pretndersEntityMapper.fromEntityToModel(userInDb)
    }

    override fun findIDByReference(reference: String): Long {
        return pretndersRepository.findIDByReference(reference)
    }
}