package pretnders.api.pretnders.persistence

import io.quarkus.logging.Log
import io.quarkus.test.InjectMock
import io.quarkus.test.TestTransaction
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.hibernate.exception.ConstraintViolationException
import org.junit.jupiter.api.*
import org.mockito.Mockito
import org.mockito.kotlin.*
import pretnders.api.pretnders.domain.models.CreatePretenderCommand
import pretnders.api.pretnders.domain.ports.out.AddPretndersOut
import pretnders.api.pretnders.persistence.entities.PretndersEntity
import pretnders.api.pretnders.persistence.mappers.PretndersEntityMapper
import pretnders.api.pretnders.persistence.repositories.PretndersQueryRepository
import pretnders.api.shared.errors.ApplicationException
import pretnders.api.shared.utils.generators.UUIDGenerator.getNewUUID
import java.sql.SQLException
import java.sql.Timestamp
import java.time.Instant

@QuarkusTest
@DisplayName("Add Pretenders persistence UT \n")
class CreatePretndersUnitTest {
    @Inject
    lateinit var addPretndersOut: AddPretndersOut
    @InjectMock
    lateinit var pretndersQueryRepository: PretndersQueryRepository

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
        whenever(pretndersQueryRepository.persistAndFlush(entityToSave)).thenCallRealMethod()
        addPretndersOut.addPretender(createPretenderCommand)
        /**
         * Verify
         */
        verify(pretndersQueryRepository).persistAndFlush(pretndersEntityCaptor.capture())
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
        whenever(pretndersQueryRepository.persistAndFlush(any())).thenThrow(ConstraintViolationException
            ("Exception", SQLException("Exception"), MAIL_KEY))
        Log.info(createPretenderCommand.toString())

        Log.info(entityToSave.toString())
        /**
         * Verify
         */
        assertThrows<ApplicationException> { addPretndersOut.addPretender(createPretenderCommand) }
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
        whenever(pretndersQueryRepository.persistAndFlush(any())).thenThrow(ConstraintViolationException
            ("Exception", SQLException("Exception"), REFERENCE_KEY))
        /**
         * Verify
         */
        assertThrows<ApplicationException> { addPretndersOut.addPretender(createPretenderCommand) }
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
        whenever(pretndersQueryRepository.persistAndFlush(any())).thenThrow(ConstraintViolationException
            ("Exception", SQLException("Exception"), PHONE_KEY))

        /**
         * Verify
         */
        assertThrows<ApplicationException> { addPretndersOut.addPretender(createPretenderCommand) }
        assert(createPretenderCommand.mail == "sa.benn@gmail.com")
    }
}