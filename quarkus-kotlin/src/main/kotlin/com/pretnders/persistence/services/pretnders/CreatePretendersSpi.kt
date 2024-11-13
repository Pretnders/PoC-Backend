package com.pretnders.persistence.services.pretnders

import com.pretnders.domain.models.pretnders.CreatePretenderCommand
import com.pretnders.domain.ports.out.CreatePretendersOut
import com.pretnders.persistence.mappers.pretnders.PretndersEntityMapper
import com.pretnders.persistence.repositories.PretndersRepository
import com.pretnders.persistence.services.utils.ExceptionsHandler
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.hibernate.exception.ConstraintViolationException

@Transactional
@ApplicationScoped
class CreatePretendersSpi : CreatePretendersOut {


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
            ExceptionsHandler.handlePersistenceExceptions(e)
        }
    }



}