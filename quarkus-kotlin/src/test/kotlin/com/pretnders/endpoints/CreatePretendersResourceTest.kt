package com.pretnders.endpoints

import com.fasterxml.jackson.databind.ObjectMapper
import com.pretnders.application.utils.CookieUtils
import com.pretnders.application.dto.requests.CreatePretenderRequest
import com.pretnders.application.mappers.PretndersDtoMappers
import com.pretnders.domain.errors.ApplicationException
import com.pretnders.domain.errors.ApplicationExceptionsEnum
import com.pretnders.domain.models.UserTypes
import com.pretnders.domain.models.pretnders.CreatePretenderCommand
import com.pretnders.domain.models.pretnders.UserBasicInformations
import com.pretnders.domain.ports.`in`.CreatePretendersIn
import com.pretnders.domain.ports.`in`.CsrfTokenGeneratorIn
import com.pretnders.domain.services.JwtTokenGenerator
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
class CreatePretendersResourceTest {

    @InjectMock
    private lateinit var csrfTokenGeneratorIn: CsrfTokenGeneratorIn

    @InjectMock
    private lateinit var cookieUtils: CookieUtils

    @Inject
    private lateinit var jwtTokenGenerator: JwtTokenGenerator

    private val pretndersDtoMappers: PretndersDtoMappers = Mockito.mock(PretndersDtoMappers::class.java)

    @InjectMock
    private lateinit var createPretendersIn: CreatePretendersIn

    companion object {

        private val mapper = ObjectMapper()
    }

    @Test
    @DisplayName("Should create pretender")
    fun testCreateAdminOkRequest() {
        val csrfToken = "ATOKEN"
        val adminRequest = CreatePretenderRequest(
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
            UserTypes.ADMIN.name,
            "ABCDEF",
            jwtToken,
            false
        )
        val createPretenderCommandCaptor = argumentCaptor<CreatePretenderCommand>()

        whenever(pretndersDtoMappers.fromCreationRequest(adminRequest)).doReturn(mappedRequest)
        whenever(
            createPretendersIn.createPretender(
                mappedRequest
            )
        ).doReturn(userBasicInformations)
        whenever(cookieUtils.setUpCookie("Bearer", userBasicInformations.jwToken))
            .doReturn(
                NewCookie.Builder("Bearer").value(jwtToken).maxAge(64800).httpOnly(true).path("/")
                    .build()
            )
        whenever(csrfTokenGeneratorIn.generateToken(adminRequest.mail)).doReturn(csrfToken)
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

        verify(createPretendersIn).createPretender(createPretenderCommandCaptor.capture())
        commonAsserts(createPretenderCommandCaptor, mappedRequest)
        assertEquals(201, res.statusCode())
        assertEquals(res.cookie("x-csrf-token"), csrfToken)
        assertEquals(res.cookie("Bearer"), jwtToken)

    }

    @Test
    @DisplayName("Should get bad request")
    fun testCreateAdminBadRequest() {
        val createPretenderCommandCaptor = argumentCaptor<CreatePretenderCommand>()
        val pretenderRequest = CreatePretenderRequest(
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
            createPretendersIn.createPretender(
                mappedRequest
            )
        ).doThrow(ApplicationException(ApplicationExceptionsEnum.CREATE_USER_INVALID_PHONE_NUMBER))
        val json: String = mapper.writeValueAsString(pretenderRequest)

        val res = RestAssured.given()
            .header("Content-Type", "application/json")
            .body(json)
            .`when`()
            .post("/create-pretnder")
            .then().extract()

        verify(createPretendersIn).createPretender(createPretenderCommandCaptor.capture())

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