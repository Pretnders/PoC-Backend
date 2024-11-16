package pretnders.api.pretnders.persistence.services

import pretnders.api.shared.errors.ApplicationException
import pretnders.api.shared.errors.ApplicationExceptionsEnum
import pretnders.api.pretnders.domain.models.Pretnder
import pretnders.api.pretnders.domain.ports.out.FindPretndersOut
import pretnders.api.pretnders.persistence.mappers.PretndersEntityMapper
import pretnders.api.pretnders.persistence.repositories.PretndersQueryRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject

@ApplicationScoped
class FindPretndersSpi: FindPretndersOut {

    //TODO Unit test this shit

    @Inject
    @field:Default
    private lateinit var pretndersQueryRepository: PretndersQueryRepository

    @Inject
    @field:Default
    private lateinit var pretndersEntityMapper: PretndersEntityMapper

    override fun findByIdentifier(identifier: String): Pretnder {
        val userInDb = pretndersQueryRepository.findByIdentifier(identifier).orElseThrow { ApplicationException(
            ApplicationExceptionsEnum.LOGIN_USER_NOT_FOUND) }
        return pretndersEntityMapper.fromEntityToModel(userInDb)
    }

    override fun findIDByReference(reference: String): Long {
        return pretndersQueryRepository.findIDByReference(reference)
    }
}