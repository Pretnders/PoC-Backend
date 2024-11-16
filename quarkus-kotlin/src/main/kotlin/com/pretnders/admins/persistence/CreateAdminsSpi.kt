package com.pretnders.admins.persistence

import com.pretnders.admins.domain.CreateAdminCommand
import com.pretnders.admins.domain.CreateAdminOut
import com.pretnders.shared.utils.handlers.PersistenceExceptionsHandler.handlePersistenceExceptions
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.hibernate.exception.ConstraintViolationException


@ApplicationScoped
class CreateAdminsSpi: CreateAdminOut {
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