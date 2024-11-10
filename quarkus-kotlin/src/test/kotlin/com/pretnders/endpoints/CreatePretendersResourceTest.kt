package com.pretnders.endpoints

import com.fasterxml.jackson.databind.ObjectMapper
import com.pretnders.application.controllers.CookieUtils
import com.pretnders.application.dto.requests.CreatePretenderRequest
import com.pretnders.application.dto.responses.CreateAccountResponse
import com.pretnders.application.mappers.UsersDtoMappers
import com.pretnders.domain.errors.ApplicationException
import com.pretnders.domain.errors.ApplicationExceptionsEnum
import com.pretnders.domain.models.UserTypes
import com.pretnders.domain.models.commands.users.CreatePretenderCommand
import com.pretnders.domain.models.users.UserBasicInformations
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

    private val usersDtoMappers: UsersDtoMappers = Mockito.mock(UsersDtoMappers::class.java)

    @InjectMock
    private lateinit var createPretendersIn: CreatePretendersIn

    companion object {
        val adminRequest = CreatePretenderRequest(
            "Nick",
            "Sid",
            "Bennaceur",
            "test@test.com",
            "123S0leil!",
            "0613511351"
        )
        val mappedRequest: CreatePretenderCommand = CreatePretenderCommand(
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
        private val mapper = ObjectMapper()
        private val json: String = mapper.writeValueAsString(adminRequest)
    }

    @Test
    @DisplayName("Should create pretender")
    fun testCreateAdminOkRequest() {
        val csrfToken = "ATOKEN"
        val jwtToken = jwtTokenGenerator.getToken(adminRequest.mail, UserTypes.ADMIN.name)
        val userBasicInformations = UserBasicInformations(
            UserTypes.ADMIN.name,
            "ABCDEF",
            jwtToken,
            false
        )
        val createPretenderCommandCaptor = argumentCaptor<CreatePretenderCommand>()

        whenever(usersDtoMappers.fromCreationRequest(adminRequest)).thenReturn(mappedRequest)
        whenever(
            createPretendersIn.createPretender(
                any()
            )
        ).thenReturn(userBasicInformations)
        whenever(cookieUtils.setUpCookie("Bearer", userBasicInformations.jwToken))
            .thenReturn(
                NewCookie.Builder("Bearer").value(jwtToken).maxAge(64800).httpOnly(true).path("/")
                    .build()
            )
        whenever(csrfTokenGeneratorIn.generateToken(adminRequest.mail)).thenReturn(csrfToken)
        whenever(cookieUtils.setUpCookie("csrf-token", csrfToken)).thenReturn(
            NewCookie.Builder("csrf-token").value
                (csrfToken).maxAge(64800).httpOnly(false).path("/").build()
        )

        val res = RestAssured.given()
            .header("Content-Type", "application/json")
            .body(json)
            .`when`()
            .post("/create-pretnder")
            .then().extract()

        verify(createPretendersIn).createPretender(createPretenderCommandCaptor.capture())

        commonAsserts(createPretenderCommandCaptor)
        val responseBody = res.body().`as`(CreateAccountResponse::class.java)
        assertTrue(responseBody.reference == userBasicInformations.reference)
        assertEquals(res.statusCode(), 200)
        assertEquals(res.cookie("csrf-token"), csrfToken)
        assertEquals(res.cookie("Bearer"), jwtToken)

    }

    @Test
    @DisplayName("Should get bad request")
    fun testCreateAdminBadRequest() {
        val createPretenderCommandCaptor = argumentCaptor<CreatePretenderCommand>()

        whenever(usersDtoMappers.fromCreationRequest(adminRequest)).thenReturn(mappedRequest)
        whenever(
            createPretendersIn.createPretender(
                any()
            )
        ).thenThrow(ApplicationException(ApplicationExceptionsEnum.CREATE_USER_INVALID_PHONE_NUMBER))

        val res = RestAssured.given()
            .header("Content-Type", "application/json")
            .body(json)
            .`when`()
            .post("/create-pretnder")
            .then().extract()

        verify(createPretendersIn).createPretender(createPretenderCommandCaptor.capture())

        commonAsserts(createPretenderCommandCaptor)
        assertEquals(res.statusCode(), 400)
        val responseBody = res.body().`as`(ApplicationException::class.java)
        assertTrue(responseBody.origin == ApplicationExceptionsEnum.CREATE_USER_INVALID_PHONE_NUMBER.origin)
        assertTrue(responseBody.message == ApplicationExceptionsEnum.CREATE_USER_INVALID_PHONE_NUMBER.message)
    }

    private fun commonAsserts(
        createPretenderCommandCaptor: KArgumentCaptor<CreatePretenderCommand>
    ) {
        assertTrue(createPretenderCommandCaptor.firstValue.mail == adminRequest.mail)
        assertTrue(createPretenderCommandCaptor.firstValue.firstName == adminRequest.firstName)
        assertTrue(createPretenderCommandCaptor.firstValue.lastName == adminRequest.lastName)
        assertTrue(createPretenderCommandCaptor.firstValue.password == adminRequest.password)
        assertTrue(createPretenderCommandCaptor.firstValue.phoneNumber == adminRequest.phoneNumber)
    }


}