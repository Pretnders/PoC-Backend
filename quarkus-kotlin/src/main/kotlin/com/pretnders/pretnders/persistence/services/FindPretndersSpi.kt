package com.pretnders.pretnders.persistence.services

import com.pretnders.shared.errors.ApplicationException
import com.pretnders.shared.errors.ApplicationExceptionsEnum
import com.pretnders.pretnders.domain.models.Pretnder
import com.pretnders.pretnders.domain.ports.out.FindPretndersOut
import com.pretnders.pretnders.persistence.mappers.PretndersEntityMapper
import com.pretnders.pretnders.persistence.repositories.PretndersRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject

@ApplicationScoped
class FindPretndersSpi: FindPretndersOut {

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