package com.pretnders.pretnders.persistence.services

import com.pretnders.pretnders.persistence.mappers.PretndersEntityMapper
import com.pretnders.pretnders.domain.models.CreatePretenderCommand
import com.pretnders.pretnders.domain.ports.out.AddPretndersOut
import com.pretnders.pretnders.persistence.repositories.PretndersRepository
import com.pretnders.shared.utils.handlers.PersistenceExceptionsHandler
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.hibernate.exception.ConstraintViolationException

@Transactional
@ApplicationScoped
class AddPretendersSpi : AddPretndersOut {


    @Inject
    @field:Default
    private lateinit var pretndersRepository: PretndersRepository

    @Inject
    @field:Default
    private lateinit var pretndersEntityMapper: PretndersEntityMapper

    override fun addPretender(pretender: CreatePretenderCommand) {
        val userEntity = pretndersEntityMapper.fromCreateCommandToEntity(pretender)
        try {
            Log.debug(userEntity.toString())
            pretndersRepository.persistAndFlush(userEntity)
        } catch (e: ConstraintViolationException) {
            PersistenceExceptionsHandler.handlePersistenceExceptions(e)
        }
    }



}