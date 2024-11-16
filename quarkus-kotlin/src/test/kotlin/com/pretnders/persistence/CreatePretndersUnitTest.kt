package com.pretnders.persistence

import com.pretnders.shared.errors.ApplicationException
import com.pretnders.pretnders.domain.models.CreatePretenderCommand
import com.pretnders.pretnders.domain.ports.out.CreatePretendersOut
import com.pretnders.shared.utils.generators.UUIDGenerator.getNewUUID
import com.pretnders.pretnders.persistence.entities.PretndersEntity
import com.pretnders.pretnders.persistence.mappers.PretndersEntityMapper
import com.pretnders.pretnders.persistence.repositories.PretndersRepository
import io.quarkus.logging.Log
import io.quarkus.test.InjectMock
import io.quarkus.test.TestTransaction
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.hibernate.exception.ConstraintViolationException
import org.junit.jupiter.api.*
import org.mockito.Mockito
import org.mockito.kotlin.*
import java.sql.SQLException
import java.sql.Timestamp
import java.time.Instant

@QuarkusTest
@DisplayName("Add Pretenders persistence UT \n")
class CreatePretndersUnitTest {
    @Inject
    lateinit var createPretendersOut: CreatePretendersOut
    @InjectMock
    lateinit var pretndersRepository: PretndersRepository

    private val pretndersEntityMapper: PretndersEntityMapper = Mockito.mock(PretndersEntityMapper::class.java)

    val createPretenderCommand = CreatePretenderCommand(
        "Nick",
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

    companion object {
        const val MAIL_KEY = "uq_pretenders_mail"
        const val PHONE_KEY = "uq_pretenders_phone"
        const val REFERENCE_KEY = "uq_pretenders_reference"
    }
    val entityToSave = PretndersEntity()
    val entityToReturn = PretndersEntity()
    @BeforeEach
    fun setUp() {
        entityToSave.firstName = createPretenderCommand.firstName
        entityToSave.lastName = createPretenderCommand.lastName
        entityToSave.password = createPretenderCommand.password
        entityToSave.mail = createPretenderCommand.mail
        entityToSave.phoneNumber = createPretenderCommand.phoneNumber
        entityToSave.reference = createPretenderCommand.reference
        entityToSave.verificationCode = createPretenderCommand.verificationCode
        entityToSave.verificationCodeTimestamp = createPretenderCommand.verificationCodeTimestamp


        entityToReturn.id = 1L
        entityToReturn.firstName = createPretenderCommand.firstName
        entityToReturn.lastName = createPretenderCommand.lastName
        entityToReturn.password = createPretenderCommand.password
        entityToReturn.mail = createPretenderCommand.mail
        entityToReturn.phoneNumber = createPretenderCommand.phoneNumber
        entityToReturn.reference = createPretenderCommand.reference
        entityToReturn.verificationCode = createPretenderCommand.verificationCode
        entityToReturn.verificationCodeTimestamp = createPretenderCommand.verificationCodeTimestamp
    }


    @Test
    @TestTransaction
    @DisplayName("should create pretenders \n")
    @Order(1)
    fun testCreatePretenders() {
        Log.info(createPretenderCommand.toString())

        Log.info(entityToSave.toString())
        /**
         * Prepare
         */
        val pretndersEntityCaptor = argumentCaptor<PretndersEntity>()
        /**
         * Arrange
         */
        whenever(pretndersEntityMapper.fromCreateCommandToEntity(createPretenderCommand)).doReturn(entityToSave)
        whenever(pretndersRepository.persistAndFlush(entityToSave)).thenCallRealMethod()
        createPretendersOut.addPretender(createPretenderCommand)
        /**
         * Verify
         */
        verify(pretndersRepository).persistAndFlush(pretndersEntityCaptor.capture())
        assert(createPretenderCommand.firstName == pretndersEntityCaptor.firstValue.firstName)
        assert(createPretenderCommand.lastName == pretndersEntityCaptor.firstValue.lastName)
        assert(createPretenderCommand.reference == pretndersEntityCaptor.firstValue.reference)
        assert(createPretenderCommand.mail == pretndersEntityCaptor.firstValue.mail)
        assert(createPretenderCommand.password == pretndersEntityCaptor.firstValue.password)
        assert(createPretenderCommand.phoneNumber == pretndersEntityCaptor.firstValue.phoneNumber)
        assert(createPretenderCommand.verificationCode == pretndersEntityCaptor.firstValue.verificationCode)
        assert(createPretenderCommand.verificationCodeTimestamp == pretndersEntityCaptor.firstValue
            .verificationCodeTimestamp)
        assert(createPretenderCommand.accountVerified == pretndersEntityCaptor.firstValue.accountVerifiedStatus)
    }

    @Test
    @TestTransaction
    @DisplayName("should throw constraint violation exception on mail \n")
    @Order(2)
    fun shouldThrowConstraintViolationExceptionOnMail() {
        /**
         * Arrange
         */
        whenever(pretndersEntityMapper.fromCreateCommandToEntity(createPretenderCommand)).doReturn(entityToSave)
        whenever(pretndersRepository.persistAndFlush(any())).thenThrow(ConstraintViolationException
            ("Exception", SQLException("Exception"), MAIL_KEY))
        Log.info(createPretenderCommand.toString())

        Log.info(entityToSave.toString())
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
         * Arrange
         */  Log.info(createPretenderCommand.toString())

        Log.info(entityToSave.toString())
        whenever(pretndersEntityMapper.fromCreateCommandToEntity(createPretenderCommand)).doReturn(entityToSave)
        whenever(pretndersRepository.persistAndFlush(any())).thenThrow(ConstraintViolationException
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
         * Arrange
         */  Log.info(createPretenderCommand.toString())

        Log.info(entityToSave.toString())
        whenever(pretndersEntityMapper.fromCreateCommandToEntity(createPretenderCommand)).doReturn(entityToSave)
        whenever(pretndersRepository.persistAndFlush(any())).thenThrow(ConstraintViolationException
            ("Exception", SQLException("Exception"), PHONE_KEY))

        /**
         * Verify
         */
        assertThrows<ApplicationException> { createPretendersOut.addPretender(createPretenderCommand) }
        assert(createPretenderCommand.mail == "sa.benn@gmail.com")
    }
}