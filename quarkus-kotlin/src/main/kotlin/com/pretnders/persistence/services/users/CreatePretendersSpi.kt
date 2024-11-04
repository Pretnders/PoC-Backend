package com.pretnders.persistence.services.users

import com.pretnders.domain.errors.ApplicationException
import com.pretnders.domain.errors.ApplicationExceptionsEnum
import com.pretnders.domain.models.commands.users.CreatePretenderCommand
import com.pretnders.domain.ports.out.CreatePretendersOut
import com.pretnders.persistence.mappers.users.UsersEntityMapper
import com.pretnders.persistence.repositories.PretendersRepository
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
    companion object {
        const val MAIL_KEY = "uq_user_mail"
        const val PHONE_KEY = "uq_user_phone"
        const val REFERENCE_KEY = "uq_user_reference"
    }

    @Inject
    @field:Default
    private lateinit var pretendersRepository: PretendersRepository

    @Inject
    @field:Default
    private lateinit var usersEntityMapper: UsersEntityMapper

    override fun addPretender(pretender: CreatePretenderCommand) {
        val userEntity = usersEntityMapper.fromCreateUserToEntity(pretender)
        try {
            Log.debug(userEntity.toString())
            pretendersRepository.persist(userEntity)
            pretendersRepository.flush()
        } catch (e: ConstraintViolationException) {
            handleExceptions(e)
        }
    }


    private fun handleExceptions(e:ConstraintViolationException):Throws {
        Log.debug(String.format("Error while adding admin : %s", e.message))
        Log.debug(String.format("Error while adding admin : %s", e.constraintName))
        when {
            e.constraintName.equals(MAIL_KEY) -> {
                throw ApplicationException(ApplicationExceptionsEnum.CREATE_USER_DUPLICATE_MAIL)
            }
            e.constraintName.equals(PHONE_KEY) -> {
                throw ApplicationException(ApplicationExceptionsEnum.CREATE_USER_DUPLICATE_PHONE_NUMBER)
            }
            e.constraintName.equals(REFERENCE_KEY) -> {
                throw ApplicationException(ApplicationExceptionsEnum.CREATE_USER_DUPLICATE_REFERENCE)
            }
            else -> {
                throw ApplicationException(ApplicationExceptionsEnum.ERROR)
            }
        }
    }
}