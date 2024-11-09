package com.pretnders.persistence.services.users

import com.pretnders.domain.errors.ApplicationException
import com.pretnders.domain.errors.ApplicationExceptionsEnum
import com.pretnders.domain.models.commands.users.CreatePretenderCommand
import com.pretnders.domain.ports.out.CreatePretendersOut
import com.pretnders.persistence.mappers.users.PretndersEntityMapper
import com.pretnders.persistence.repositories.PretendersRepository
import com.pretnders.persistence.services.utils.ExceptionsHandler
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.hibernate.exception.ConstraintViolationException
import io.quarkus.logging.Log;
import kotlin.jvm.Throws

@Transactional
@ApplicationScoped
class CreatePretendersSpi : CreatePretendersOut {


    @Inject
    @field:Default
    private lateinit var pretendersRepository: PretendersRepository

    @Inject
    @field:Default
    private lateinit var pretndersEntityMapper: PretndersEntityMapper

    override fun addPretender(pretender: CreatePretenderCommand) {
        val userEntity = pretndersEntityMapper.fromCreatePretnderToEntity(pretender)
        try {
            Log.debug(userEntity.toString())
            pretendersRepository.persistAndFlush(userEntity)
        } catch (e: ConstraintViolationException) {
            ExceptionsHandler.handlePersistenceExceptions(e)
        }
    }



}