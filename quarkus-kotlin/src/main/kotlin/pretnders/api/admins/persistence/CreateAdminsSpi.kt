package pretnders.api.admins.persistence

import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import org.hibernate.exception.ConstraintViolationException
import pretnders.api.admins.domain.CreateAdminCommand
import pretnders.api.admins.domain.CreateAdminOut
import pretnders.api.shared.utils.handlers.PersistenceExceptionsHandler.handlePersistenceExceptions


@ApplicationScoped
class CreateAdminsSpi(
    private val adminsEntityMapper: AdminsEntityMapper,
    private val adminsQueryRepository: AdminsQueryRepository

): CreateAdminOut {

    override fun addAdmin(createAdminCommand: CreateAdminCommand) {
        val entity = adminsEntityMapper.fomAdminCommandToEntity(createAdminCommand)
        try {
            Log.debug(entity.toString())
            adminsQueryRepository.persistAndFlush(entity)
        } catch (e: ConstraintViolationException) {
            handlePersistenceExceptions(e)
        }
    }
}