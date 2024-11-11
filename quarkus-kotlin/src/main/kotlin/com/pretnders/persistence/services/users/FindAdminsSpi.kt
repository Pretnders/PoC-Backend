package com.pretnders.persistence.services.users

import com.pretnders.domain.errors.ApplicationException
import com.pretnders.domain.errors.ApplicationExceptionsEnum
import com.pretnders.domain.models.admins.Admins
import com.pretnders.domain.ports.out.FindAdminsOut
import com.pretnders.persistence.mappers.pretnders.AdminsEntityMapper
import com.pretnders.persistence.repositories.AdminsRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject

@ApplicationScoped
class FindAdminsSpi:FindAdminsOut {

    @Inject
    @field:Default
    private lateinit var adminsRepository: AdminsRepository

    @Inject
    @field:Default
    private lateinit var adminsEntityMapper: AdminsEntityMapper

    override fun findByIdentifier(identifier: String): Admins {
        val adminInDb = adminsRepository.findByIdentifier(identifier).orElseThrow { ApplicationException(
            ApplicationExceptionsEnum.LOGIN_USER_NOT_FOUND) }
        return adminsEntityMapper.fromEntityToModel(adminInDb)
    }

    override fun findIDByReference(reference: String): Long {
        return adminsRepository.findIDByReference(reference)
    }
}