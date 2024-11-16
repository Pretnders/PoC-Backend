package pretnders.api.shared.utils.handlers

import pretnders.api.shared.errors.ApplicationException
import pretnders.api.shared.errors.ApplicationExceptionsEnum
import io.quarkus.logging.Log
import org.hibernate.exception.ConstraintViolationException
import kotlin.jvm.Throws

object PersistenceExceptionsHandler {
    private const val PRETENDERS_MAIL_KEY = "uq_pretenders_mail"
    private const val PRETENDERS_PHONE_KEY = "uq_pretenders_phone"
    private const val PRETENDERS_REFERENCE_KEY = "uq_pretenders_reference"
    private const val ADMINS_MAIL_KEY = "uq_admins_mail"
    private const val ADMINS_PHONE_KEY = "uq_admins_phone"
    private const val ADMINS_REFERENCE_KEY = "uq_admins_reference"
    private const val ADMINS_NICKNAME_KEY = "uq_admins_nickname"

    fun handlePersistenceExceptions(e: ConstraintViolationException): Throws {
        Log.error(String.format("Error while adding entity : %s", e.message))
        Log.error(String.format("Error while adding entity : %s", e.constraintName))
        when {
            e.constraintName.equals(PRETENDERS_MAIL_KEY) -> {
                throw ApplicationException(ApplicationExceptionsEnum.CREATE_USER_DUPLICATE_MAIL)
            }
            e.constraintName.equals(PRETENDERS_PHONE_KEY) -> {
                throw ApplicationException(ApplicationExceptionsEnum.CREATE_USER_DUPLICATE_PHONE_NUMBER)
            }
            e.constraintName.equals(PRETENDERS_REFERENCE_KEY) -> {
                throw ApplicationException(ApplicationExceptionsEnum.CREATE_USER_DUPLICATE_REFERENCE)
            }
            e.constraintName.equals(ADMINS_MAIL_KEY) -> {
                throw ApplicationException(ApplicationExceptionsEnum.CREATE_ADMIN_DUPLICATE_MAIL)
            }
            e.constraintName.equals(ADMINS_PHONE_KEY) -> {
                throw ApplicationException(ApplicationExceptionsEnum.CREATE_ADMIN_DUPLICATE_PHONE)
            }
            e.constraintName.equals(ADMINS_REFERENCE_KEY) -> {
                throw ApplicationException(ApplicationExceptionsEnum.CREATE_ADMIN_DUPLICATE_REFERENCE)
            }
            e.constraintName.equals(ADMINS_NICKNAME_KEY) -> {
                throw ApplicationException(ApplicationExceptionsEnum.CREATE_ADMIN_DUPLICATE_NICKNAME)
            }
            else -> {
                throw ApplicationException(ApplicationExceptionsEnum.ERROR)
            }
        }
    }
}