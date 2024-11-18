package pretnders.api.pretnders.persistence.services

import jakarta.enterprise.context.ApplicationScoped
import pretnders.api.pretnders.domain.models.Pretnder
import pretnders.api.pretnders.domain.ports.out.FindPretndersOut
import pretnders.api.pretnders.persistence.mappers.PretndersEntityMapper
import pretnders.api.pretnders.persistence.repositories.PretndersQueryRepository
import pretnders.api.shared.errors.ApplicationException
import pretnders.api.shared.errors.ApplicationExceptionsEnum

@ApplicationScoped
class FindPretndersSpi(
    private val pretndersQueryRepository: PretndersQueryRepository,
    private val pretndersEntityMapper: PretndersEntityMapper
): FindPretndersOut {



    override fun findByIdentifier(identifier: String): Pretnder {
        val userInDb = pretndersQueryRepository.findByIdentifier(identifier).orElseThrow { ApplicationException(
            ApplicationExceptionsEnum.LOGIN_USER_NOT_FOUND) }
        return pretndersEntityMapper.fromEntityToModel(userInDb)
    }

    override fun findIDByReference(reference: String): Long {
        return pretndersQueryRepository.findIDByReference(reference)!!
    }
}