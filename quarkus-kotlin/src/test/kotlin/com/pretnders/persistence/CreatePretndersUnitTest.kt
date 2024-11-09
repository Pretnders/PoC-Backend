package com.pretnders.persistence

import com.pretnders.domain.errors.ApplicationException
import com.pretnders.domain.models.commands.users.CreatePretenderCommand
import com.pretnders.domain.ports.out.CreatePretendersOut
import com.pretnders.domain.utils.UUIDGenerator.getNewUUID
import com.pretnders.persistence.entities.PretendersEntity
import com.pretnders.persistence.mappers.users.PretndersEntityMapper
import com.pretnders.persistence.repositories.PretendersRepository
import io.quarkus.logging.Log
import io.quarkus.test.InjectMock
import io.quarkus.test.TestTransaction
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.hibernate.exception.ConstraintViolationException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.sql.SQLException
import java.sql.Timestamp
import java.time.Instant

@QuarkusTest
@DisplayName("Add Pretenders persistence UT \n")
class CreatePretndersUnitTest {
    @Inject
    lateinit var createPretendersOut: CreatePretendersOut
    @InjectMock
    lateinit var pretendersRepository: PretendersRepository

    private val usersDtoMappers: PretndersEntityMapper = Mockito.mock(PretndersEntityMapper::class.java)

    companion object {
        const val MAIL_KEY = "uq_user_mail"
        const val PHONE_KEY = "uq_user_phone"
        const val REFERENCE_KEY = "uq_user_reference"
    }

    @Test
    @TestTransaction
    @DisplayName("should create pretenders \n")
    @Order(1)
    fun testCreatePretenders() {
        /**
         * Prepare
         */
        val createPretenderCommand = CreatePretenderCommand(
            "Sid",
            "Bennaceur",
            "sa.benn@gmail.com",
            "SomePPassw0rd!",
            "0712121212",
            getNewUUID(),
            "132456789",
            Timestamp.from(Instant.now()),
            false
        )
        val entityToSave = PretendersEntity()
        entityToSave.firstName = createPretenderCommand.firstName
        entityToSave.lastName = createPretenderCommand.lastName
        entityToSave.password = createPretenderCommand.password
        entityToSave.mail = createPretenderCommand.mail
        entityToSave.phoneNumber = createPretenderCommand.phoneNumber
        entityToSave.reference = createPretenderCommand.reference
        entityToSave.verificationCode = createPretenderCommand.verificationCode
        entityToSave.verificationCodeTimestamp = createPretenderCommand.verificationCodeTimestamp
        val entityToReturn = PretendersEntity()
        entityToReturn.id = 1L
        entityToReturn.firstName = createPretenderCommand.firstName
        entityToReturn.lastName = createPretenderCommand.lastName
        entityToReturn.password = createPretenderCommand.password
        entityToReturn.mail = createPretenderCommand.mail
        entityToReturn.phoneNumber = createPretenderCommand.phoneNumber
        entityToReturn.reference = createPretenderCommand.reference
        entityToReturn.verificationCode = createPretenderCommand.verificationCode
        entityToReturn.verificationCodeTimestamp = createPretenderCommand.verificationCodeTimestamp
        val pretendersEntityCaptor = argumentCaptor<PretendersEntity>()

        /**
         * Arrange
         */
        whenever(usersDtoMappers.fromCreatePretnderToEntity(createPretenderCommand)).thenReturn(entityToSave)
        whenever(pretendersRepository.persistAndFlush(entityToSave)).thenCallRealMethod()
        createPretendersOut.addPretender(createPretenderCommand)

        /**
         * Verify
         */
        verify(pretendersRepository).persistAndFlush(pretendersEntityCaptor.capture())

        Log.info(pretendersEntityCaptor.toString())

        assert(createPretenderCommand.firstName == pretendersEntityCaptor.firstValue.firstName)
        assert(createPretenderCommand.lastName == pretendersEntityCaptor.firstValue.lastName)
        assert(createPretenderCommand.reference == pretendersEntityCaptor.firstValue.reference)
        assert(createPretenderCommand.mail == pretendersEntityCaptor.firstValue.mail)
        assert(createPretenderCommand.password == pretendersEntityCaptor.firstValue.password)
        assert(createPretenderCommand.phoneNumber == pretendersEntityCaptor.firstValue.phoneNumber)
        assert(createPretenderCommand.verificationCode == pretendersEntityCaptor.firstValue.verificationCode)
        assert(createPretenderCommand.verificationCodeTimestamp == pretendersEntityCaptor.firstValue
            .verificationCodeTimestamp)
        assert(createPretenderCommand.accountVerified == pretendersEntityCaptor.firstValue.accountVerifiedStatus)
    }

    @Test
    @TestTransaction
    @DisplayName("should throw constraint violation exception on mail \n")

    @Order(2)

    fun shouldThrowConstraintViolationExceptionOnMail() {
        /**
         * Prepare
         */
        val createPretenderCommand = CreatePretenderCommand(
            "Sid",
            "Bennaceur",
            "sa.benn@gmail.com",
            "SomePPassw0rd!",
            "0712121212",
            getNewUUID(),
            "132456789",
            Timestamp.from(Instant.now()),
            false
        )
        val entityToSave = PretendersEntity()
        entityToSave.firstName = createPretenderCommand.firstName
        entityToSave.lastName = createPretenderCommand.lastName
        entityToSave.password = createPretenderCommand.password
        entityToSave.mail = createPretenderCommand.mail
        entityToSave.phoneNumber = createPretenderCommand.phoneNumber
        entityToSave.reference = createPretenderCommand.reference
        entityToSave.verificationCode = createPretenderCommand.verificationCode
        entityToSave.verificationCodeTimestamp = createPretenderCommand.verificationCodeTimestamp
        val entityToReturn = PretendersEntity()
        entityToReturn.id = 1L
        entityToReturn.firstName = createPretenderCommand.firstName
        entityToReturn.lastName = createPretenderCommand.lastName
        entityToReturn.password = createPretenderCommand.password
        entityToReturn.mail = createPretenderCommand.mail
        entityToReturn.phoneNumber = createPretenderCommand.phoneNumber
        entityToReturn.reference = createPretenderCommand.reference
        entityToReturn.verificationCode = createPretenderCommand.verificationCode
        entityToReturn.verificationCodeTimestamp = createPretenderCommand.verificationCodeTimestamp
        /**
         * Arrange
         */
        whenever(usersDtoMappers.fromCreatePretnderToEntity(createPretenderCommand)).thenReturn(entityToSave)
        whenever(pretendersRepository.persistAndFlush(entityToSave)).thenThrow(ConstraintViolationException
            ("Exception", SQLException("Exception"), MAIL_KEY))

        /**
         * Verify
         */
        assertThrows<ApplicationException> { createPretendersOut.addPretender(createPretenderCommand) }
    }


    @Test
    @TestTransaction
    @DisplayName("should throw constraint violation exception on reference \n")
    @Order(3)
    fun shouldThrowConstraintViolationExceptionOnReference() {
        /**
         * Prepare
         */
        val createPretenderCommand = CreatePretenderCommand(
            "Sid",
            "Bennaceur",
            "sa.benn@gmail.com",
            "SomePPassw0rd!",
            "0712121212",
            getNewUUID(),
            "132456789",
            Timestamp.from(Instant.now()),
            false
        )
        val entityToSave = PretendersEntity()
        entityToSave.firstName = createPretenderCommand.firstName
        entityToSave.lastName = createPretenderCommand.lastName
        entityToSave.password = createPretenderCommand.password
        entityToSave.mail = createPretenderCommand.mail
        entityToSave.phoneNumber = createPretenderCommand.phoneNumber
        entityToSave.reference = createPretenderCommand.reference
        entityToSave.verificationCode = createPretenderCommand.verificationCode
        entityToSave.verificationCodeTimestamp = createPretenderCommand.verificationCodeTimestamp
        val entityToReturn = PretendersEntity()
        entityToReturn.id = 1L
        entityToReturn.firstName = createPretenderCommand.firstName
        entityToReturn.lastName = createPretenderCommand.lastName
        entityToReturn.password = createPretenderCommand.password
        entityToReturn.mail = createPretenderCommand.mail
        entityToReturn.phoneNumber = createPretenderCommand.phoneNumber
        entityToReturn.reference = createPretenderCommand.reference
        entityToReturn.verificationCode = createPretenderCommand.verificationCode
        entityToReturn.verificationCodeTimestamp = createPretenderCommand.verificationCodeTimestamp
        /**
         * Arrange
         */
        whenever(usersDtoMappers.fromCreatePretnderToEntity(createPretenderCommand)).thenReturn(entityToSave)
        whenever(pretendersRepository.persistAndFlush(entityToSave)).thenThrow(ConstraintViolationException
            ("Exception", SQLException("Exception"), REFERENCE_KEY))

        /**
         * Verify
         */
        assertThrows<ApplicationException> { createPretendersOut.addPretender(createPretenderCommand) }
    }

    @Test
    @TestTransaction
    @DisplayName("should throw constraint violation exception on phone number \n")
    @Order(4)
    fun shouldThrowConstraintViolationExceptionOnPhone() {
        /**
         * Prepare
         */
        val createPretenderCommand = CreatePretenderCommand(
            "Sid",
            "Bennaceur",
            "sa.benn@gmail.com",
            "SomePPassw0rd!",
            "0712121212",
            getNewUUID(),
            "132456789",
            Timestamp.from(Instant.now()),
            false
        )
        val entityToSave = PretendersEntity()
        entityToSave.firstName = createPretenderCommand.firstName
        entityToSave.lastName = createPretenderCommand.lastName
        entityToSave.password = createPretenderCommand.password
        entityToSave.mail = createPretenderCommand.mail
        entityToSave.phoneNumber = createPretenderCommand.phoneNumber
        entityToSave.reference = createPretenderCommand.reference
        entityToSave.verificationCode = createPretenderCommand.verificationCode
        entityToSave.verificationCodeTimestamp = createPretenderCommand.verificationCodeTimestamp
        val entityToReturn = PretendersEntity()
        entityToReturn.id = 1L
        entityToReturn.firstName = createPretenderCommand.firstName
        entityToReturn.lastName = createPretenderCommand.lastName
        entityToReturn.password = createPretenderCommand.password
        entityToReturn.mail = createPretenderCommand.mail
        entityToReturn.phoneNumber = createPretenderCommand.phoneNumber
        entityToReturn.reference = createPretenderCommand.reference
        entityToReturn.verificationCode = createPretenderCommand.verificationCode
        entityToReturn.verificationCodeTimestamp = createPretenderCommand.verificationCodeTimestamp
        /**
         * Arrange
         */
        whenever(usersDtoMappers.fromCreatePretnderToEntity(createPretenderCommand)).thenReturn(entityToSave)
        whenever(pretendersRepository.persistAndFlush(entityToSave)).thenThrow(ConstraintViolationException
            ("Exception", SQLException("Exception"), PHONE_KEY))

        /**
         * Verify
         */
        assertThrows<ApplicationException> { createPretendersOut.addPretender(createPretenderCommand) }

        assert(createPretenderCommand.mail == "sa.benn@gmail.com")
        Log.info("Out pute")
    }
}