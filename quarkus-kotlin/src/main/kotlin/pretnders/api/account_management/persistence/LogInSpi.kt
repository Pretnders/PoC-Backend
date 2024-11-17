package pretnders.api.account_management.persistence

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import pretnders.api.account_management.domain.LogInOut
import pretnders.api.admins.domain.Admin
import pretnders.api.admins.persistence.AdminsEntityMapper
import pretnders.api.admins.persistence.AdminsQueryRepository
import pretnders.api.pretnders.domain.models.Pretnder
import pretnders.api.pretnders.persistence.mappers.PretndersEntityMapper
import pretnders.api.pretnders.persistence.repositories.PretndersQueryRepository
import pretnders.api.shared.errors.ApplicationException
import pretnders.api.shared.errors.ApplicationExceptionsEnum

@ApplicationScoped
class LogInSpi:LogInOut {

    @Inject
    private lateinit var pretndersQueryRepository: PretndersQueryRepository

    @Inject
    private lateinit var adminsQueryRepository: AdminsQueryRepository

    @Inject
    private lateinit var pretndersEntityMapper: PretndersEntityMapper

    @Inject
    private lateinit var adminsEntityMapper: AdminsEntityMapper

    override fun findAdminByIdentifier(identifier: String): Admin {
        return adminsEntityMapper.fromEntityToModel(
            adminsQueryRepository.findByIdentifier(identifier) ?:
            throw ApplicationException(ApplicationExceptionsEnum.LOGIN_USER_NOT_FOUND)
        )
    }

    override fun findPretnderByIdentifier(identifier: String): Pretnder {
        return pretndersEntityMapper.fromEntityToModel(pretndersQueryRepository.findByIdentifier(identifier).orElseThrow {
            ApplicationException(ApplicationExceptionsEnum.LOGIN_USER_NOT_FOUND)
        })
    }
}