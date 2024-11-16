package pretnders.api.admins.persistence

import pretnders.api.admins.domain.Admin
import pretnders.api.admins.domain.FindAdminsOut
import pretnders.api.shared.errors.ApplicationException
import pretnders.api.shared.errors.ApplicationExceptionsEnum
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject

@ApplicationScoped
class FindAdminsSpi: FindAdminsOut {

    @Inject
    @field:Default
    private lateinit var adminsRepository: AdminsRepository

    @Inject
    @field:Default
    private lateinit var adminsEntityMapper: AdminsEntityMapper

    override fun findByIdentifier(identifier: String): Admin {
        val adminInDb = adminsRepository.findByIdentifier(identifier).orElseThrow { ApplicationException(
            ApplicationExceptionsEnum.LOGIN_USER_NOT_FOUND) }
        return adminsEntityMapper.fromEntityToModel(adminInDb)
    }

    override fun findIDByReference(reference: String): Long {
        return adminsRepository.findIDByReference(reference)
    }
}