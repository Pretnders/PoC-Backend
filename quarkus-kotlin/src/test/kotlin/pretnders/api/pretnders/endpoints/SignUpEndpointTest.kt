package pretnders.api.pretnders.endpoints

import com.fasterxml.jackson.databind.ObjectMapper
import pretnders.api.shared.security.CookieUtils
import pretnders.api.pretnders.application.dto.requests.AddPretenderRequest
import pretnders.api.pretnders.application.mappers.PretndersDtoMappers
import pretnders.api.shared.errors.ApplicationException
import pretnders.api.shared.errors.ApplicationExceptionsEnum
import pretnders.api.shared.utils.shared_models.UserTypes
import pretnders.api.pretnders.domain.models.CreatePretenderCommand
import pretnders.api.shared.utils.shared_models.UserBasicInformations
import pretnders.api.shared.security.CsrfTokenGeneratorIn
import pretnders.api.shared.security.JwtTokenGenerator
import pretnders.api.pretnders.domain.ports.`in`.AddPretndersIn
import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import jakarta.inject.Inject
import jakarta.ws.rs.core.NewCookie
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*

@QuarkusTest
@DisplayName("Create pretenders endpoint")
@ExtendWith(MockitoExtension::class)
class SignUpEndpointTest{

    @Inject lateinit var jwtTokenGenerator: JwtTokenGenerator
    @InjectMock
    private lateinit var csrfTokenGeneratorIn: CsrfTokenGeneratorIn

    @InjectMock
    private lateinit var cookieUtils: CookieUtils


    private val pretndersDtoMappers: PretndersDtoMappers = Mockito.mock(PretndersDtoMappers::class.java)

    @InjectMock
    private lateinit var addPretndersIn: AddPretndersIn

    companion object {

        private val mapper = ObjectMapper()
    }

    @Test
    @DisplayName("Should create pretender")
    fun testCreatePretnderOkRequest() {
        val csrfToken = "ATOKEN"
        val adminRequest = AddPretenderRequest(
            "Nick",
            "Sid",
            "Bennaceur",
            "test@test.com",
            "123S0leil!",
            "0613511351"
        )
        val mappedRequest = CreatePretenderCommand(
            adminRequest.nickname,
            adminRequest.firstName,
            adminRequest.lastName,
            adminRequest.mail,
            adminRequest.password,
            adminRequest.phoneNumber,
            null,
            null,
            null
        )
        val jwtToken = jwtTokenGenerator.getToken("123456","0712121212", adminRequest.mail, UserTypes.ADMIN.name)
        val userBasicInformations = UserBasicInformations(
            "ABCDEF",
            jwtToken,
            false
        )
        val createPretenderCommandCaptor = argumentCaptor<CreatePretenderCommand>()

        whenever(pretndersDtoMappers.fromCreationRequest(adminRequest)).doReturn(mappedRequest)
        whenever(
            addPretndersIn.createPretender(
                mappedRequest
            )
        ).doReturn(userBasicInformations)
        whenever(cookieUtils.setUpCookie("Bearer", userBasicInformations.jwToken))
            .doReturn(
                NewCookie.Builder("Bearer").value(jwtToken).maxAge(64800).httpOnly(true).path("/")
                    .build()
            )
        whenever(csrfTokenGeneratorIn.generateToken()).thenReturn(csrfToken)
        whenever(cookieUtils.setUpCookie("x-csrf-token", csrfToken)).doReturn(
            NewCookie.Builder("x-csrf-token").value
                (csrfToken).maxAge(64800).httpOnly(false).path("/").build()
        )
        val json: String = mapper.writeValueAsString(adminRequest)

        val res = RestAssured.given()
            .header("Content-Type", "application/json")
            .body(json)
            .`when`()
            .post("/create-pretnder")
            .then().extract()
        verify(csrfTokenGeneratorIn).generateToken()

        verify(addPretndersIn).createPretender(createPretenderCommandCaptor.capture())
        commonAsserts(createPretenderCommandCaptor, mappedRequest)
        assertEquals(201, res.statusCode())
        assertEquals(jwtToken, res.cookie("Bearer") )
        assertEquals(csrfToken, res.cookie("x-csrf-token") )

    }

    @Test
    @DisplayName("Should get bad request")
    fun testCreatePretnderBadRequest() {
        val createPretenderCommandCaptor = argumentCaptor<CreatePretenderCommand>()
        val pretenderRequest = AddPretenderRequest(
            "Nick",
            "Sid",
            "Bennaceur",
            "test@test.com",
            "123S0leil!",
            "0613511351"
        )
        val mappedRequest = CreatePretenderCommand(
            pretenderRequest.nickname,
            pretenderRequest.firstName,
            pretenderRequest.lastName,
            pretenderRequest.mail,
            pretenderRequest.password,
            pretenderRequest.phoneNumber,
            null,
            null,
            null
        )
        whenever(pretndersDtoMappers.fromCreationRequest(pretenderRequest)).doReturn(mappedRequest)
        whenever(
            addPretndersIn.createPretender(
                mappedRequest
            )
        ).doThrow(ApplicationException(ApplicationExceptionsEnum.CREATE_USER_INVALID_PHONE_NUMBER))
        val json: String = mapper.writeValueAsString(pretenderRequest)
        val token = "123456"
        whenever(csrfTokenGeneratorIn.generateToken()).thenReturn(token)

        val res = RestAssured.given()
            .header("Content-Type", "application/json")
            .body(json)
            .`when`()
            .post("/create-pretnder")
            .then().extract()

        verify(addPretndersIn).createPretender(createPretenderCommandCaptor.capture())

        commonAsserts(createPretenderCommandCaptor, mappedRequest)
        assertEquals(res.statusCode(), 400)
        val responseBody = res.body().`as`(ApplicationException::class.java)
        assertTrue(responseBody.origin == ApplicationExceptionsEnum.CREATE_USER_INVALID_PHONE_NUMBER.origin)
        assertTrue(responseBody.message == ApplicationExceptionsEnum.CREATE_USER_INVALID_PHONE_NUMBER.message)
    }

    private fun commonAsserts(
        createPretenderCommandCaptor: KArgumentCaptor<CreatePretenderCommand>,
        createPretenderCommand: CreatePretenderCommand
    ) {
        assertTrue(createPretenderCommandCaptor.firstValue.mail == createPretenderCommand.mail)
        assertTrue(createPretenderCommandCaptor.firstValue.firstName == createPretenderCommand.firstName)
        assertTrue(createPretenderCommandCaptor.firstValue.lastName == createPretenderCommand.lastName)
        assertTrue(createPretenderCommandCaptor.firstValue.password == createPretenderCommand.password)
        assertTrue(createPretenderCommandCaptor.firstValue.phoneNumber == createPretenderCommand.phoneNumber)
    }


}