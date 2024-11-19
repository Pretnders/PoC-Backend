package pretnders.api.pretnders.domain

import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.*
import pretnders.api.azure.domain.ports.out.StorageClientOut
import pretnders.api.pretnders.domain.models.CreatePretenderCommand
import pretnders.api.pretnders.domain.ports.`in`.AddPretndersIn
import pretnders.api.pretnders.domain.ports.out.AddPretndersOut
import pretnders.api.shared.errors.ApplicationException
import pretnders.api.shared.security.JwtTokenGenerator
import pretnders.api.shared.utils.generators.UUIDGenerator
import pretnders.api.shared.utils.mailer.Mailer
import pretnders.api.shared.utils.shared_models.UserTypes

@QuarkusTest
class AddPretndersUnitTest {
    @Inject
    private lateinit var addPretndersIn: AddPretndersIn

    @InjectMock
    private lateinit var jwtTokenGenerator: JwtTokenGenerator

    @InjectMock
    private lateinit var mailer: Mailer

    @InjectMock
    private lateinit var addPretndersOut: AddPretndersOut

    @InjectMock
    private lateinit var storageClientOut: StorageClientOut

    @InjectMock
    private lateinit var uuidGenerator: UUIDGenerator


    @Test
    @DisplayName("Should create a pretnder")
    fun shouldCreatePretnder() {
        val commandCaptor = argumentCaptor<CreatePretenderCommand>()
        val command = CreatePretenderCommand(
            "Loveur du 92",
            "Sid",
            "Bennaceur",
            "sa.bennaceur@gmail.com",
            "Passw0rd!",
            "0764017528",
        )
        val reference = "132456789"
        val mailContent = "some mail content"
        whenever(uuidGenerator.getNewUUID()).thenReturn(reference)
        whenever(mailer.generateOtpEmail(eq(command.firstName), anyString())).thenReturn(mailContent)
        doNothing().`when`(mailer).sendHtmlEmail(command.mail, "Vérification de compte", mailContent)
        val jwToken = "d5s4qd687sq65f4ds67fs41df32q4sd564sq32d165fds7465fds46"
        whenever(jwtTokenGenerator.getToken(reference, command.phoneNumber, command.mail, UserTypes.PRETNDER.name))
            .thenReturn(jwToken)
        doNothing().`when`(addPretndersOut).addPretender(command)
        doNothing().`when`(storageClientOut).createContainerForPretnder(command.phoneNumber)

        val returned = addPretndersIn.createPretender(command)

        verify(addPretndersOut).addPretender(commandCaptor.capture())
        verify(storageClientOut).createContainerForPretnder(command.phoneNumber)
        verify(jwtTokenGenerator).getToken(reference, command.phoneNumber, command.mail, UserTypes.PRETNDER.name)
        verify(mailer).sendHtmlEmail(command.mail, "Vérification de compte", mailContent)
        verify(mailer).generateOtpEmail(eq(command.firstName), anyString())
        verify(uuidGenerator).getNewUUID()
        //verifyNoMoreInteractions(uuidGenerator, storageClientOut,addPretndersOut, jwtTokenGenerator, mailer)
        assert(reference == returned.reference)
        assert(jwToken == returned.jwToken)
        assert(!returned.accountVerifiedStatus)
        assert(reference == commandCaptor.firstValue.reference)
        assert(command.mail == commandCaptor.firstValue.mail)
        assert(command.nickname == commandCaptor.firstValue.nickname)
        assert(command.firstName == commandCaptor.firstValue.firstName)
        assert(command.lastName == commandCaptor.firstValue.lastName)
        assert(command.phoneNumber == commandCaptor.firstValue.phoneNumber)
        assert(command.password == commandCaptor.firstValue.password)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", "123456789", "HAHAHAHAHA", "movéforma", "passw0rd!", "password", "psw"])
    fun shouldThrowInvalidPasswordFormatException(password: String) {

        val command = CreatePretenderCommand(
            "Loveur du 92",
            "Sid",
            "Bennaceur",
            "sa.bennaceur@gmail.com",
            password,
            "0764017528",
        )
        assertThrows<ApplicationException> {
            addPretndersIn.createPretender(command)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["a", "ab"])
    fun shouldThrowInvalidFistNameLengthException(name: String) {

        val command = CreatePretenderCommand(
            "Loveur du 92",
            "Bennaceur",
            name,
            "sa.bennaceur@gmail.com",
            "Passw0rd!",
            "0764017528",
        )
        assertThrows<ApplicationException> {
            addPretndersIn.createPretender(command)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["+3321454545", "115125","0932659878"])
    fun shouldThrowInvalidPhoneNumberFormatException(phoneNumber: String) {

        val command = CreatePretenderCommand(
            "Loveur du 92",
            "SSidou",
            "Bennaceur",
            "sa.bennaceur@gmail.com",
            "Passw0rd!",
            phoneNumber
        )
        assertThrows<ApplicationException> {
            addPretndersIn.createPretender(command)
        }
    }
}