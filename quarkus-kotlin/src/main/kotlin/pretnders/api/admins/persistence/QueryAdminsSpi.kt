package pretnders.api.admins.persistence

import jakarta.enterprise.context.ApplicationScoped
import pretnders.api.admins.domain.Admin
import pretnders.api.admins.domain.QueryAdminsOut
import pretnders.api.shared.errors.ApplicationException
import pretnders.api.shared.errors.ApplicationExceptionsEnum

@ApplicationScoped
class QueryAdminsSpi(
    private val adminsQueryRepository: AdminsQueryRepository,
    private val adminsEntityMapper: AdminsEntityMapper

): QueryAdminsOut {

    override fun findByIdentifier(identifier: String): Admin {
        val adminInDb = adminsQueryRepository.findByIdentifier(identifier) ?: throw ApplicationException(ApplicationExceptionsEnum.LOGIN_USER_NOT_FOUND)
        return adminsEntityMapper.fromEntityToModel(adminInDb)
    }

    override fun findIDByReference(reference: String): Long {
        return adminsQueryRepository.findIDByReference(reference)!!
    }
}