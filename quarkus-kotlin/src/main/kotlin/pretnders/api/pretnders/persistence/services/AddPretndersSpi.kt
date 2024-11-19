package pretnders.api.pretnders.persistence.services

import pretnders.api.pretnders.persistence.mappers.PretndersEntityMapper
import pretnders.api.pretnders.domain.models.CreatePretenderCommand
import pretnders.api.pretnders.domain.ports.out.AddPretndersOut
import pretnders.api.pretnders.persistence.repositories.PretndersQueryRepository
import pretnders.api.shared.utils.handlers.PersistenceExceptionsHandler
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.hibernate.exception.ConstraintViolationException

@Transactional
@ApplicationScoped
class AddPretndersSpi (
    private val pretndersQueryRepository: PretndersQueryRepository,
    private val pretndersEntityMapper: PretndersEntityMapper

): AddPretndersOut {




    override fun addPretender(pretender: CreatePretenderCommand) {
        val userEntity = pretndersEntityMapper.fromCreateCommandToEntity(pretender)
        try {
            Log.debug(userEntity.toString())
            pretndersQueryRepository.persistAndFlush(userEntity)
        } catch (e: ConstraintViolationException) {
            PersistenceExceptionsHandler.handlePersistenceExceptions(e)
        }
    }



}