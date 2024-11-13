package com.pretnders.persistence.services.pretnders

import com.pretnders.domain.models.admins.CreateAdminCommand
import com.pretnders.domain.ports.out.CreateAdminOut
import com.pretnders.persistence.mappers.pretnders.AdminsEntityMapper
import com.pretnders.persistence.repositories.AdminsRepository
import com.pretnders.persistence.services.utils.ExceptionsHandler.handlePersistenceExceptions
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.hibernate.exception.ConstraintViolationException


@ApplicationScoped
class CreateAdminsSpi:CreateAdminOut {
    @Inject
    private lateinit var adminsEntityMapper: AdminsEntityMapper

    @Inject
    private lateinit var adminsRepository: AdminsRepository
    override fun addAdmin(createAdminCommand: CreateAdminCommand) {
        val entity = adminsEntityMapper.fomAdminCommandToEntity(createAdminCommand)
        try {
            Log.debug(entity.toString())
            adminsRepository.persistAndFlush(entity)
        } catch (e: ConstraintViolationException) {
            handlePersistenceExceptions(e)
        }
    }
}