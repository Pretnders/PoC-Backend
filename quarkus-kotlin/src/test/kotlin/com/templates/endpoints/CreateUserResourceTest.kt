package com.templates.endpoints

import com.fasterxml.jackson.databind.ObjectMapper
import com.templates.application.controllers.CookieUtils
import com.templates.application.dto.requests.CreateAdminRequest
import com.templates.application.dto.responses.CreateUserResponse
import com.templates.application.mappers.UsersDtoMappers
import com.templates.domain.errors.ApplicationException
import com.templates.domain.errors.ApplicationExceptionsEnum
import com.templates.domain.models.commands.users.CreateUserCommand
import com.templates.domain.models.users.UserBasicInformations
import com.templates.domain.models.users.UserTypes
import com.templates.domain.ports.`in`.CreateUsersIn
import com.templates.domain.ports.`in`.CsrfTokenGeneratorIn
import com.templates.domain.services.JwtTokenGenerator
import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import io.restassured.response.ResponseBody
import io.restassured.response.ResponseBodyExtractionOptions
import jakarta.inject.Inject
import jakarta.ws.rs.core.NewCookie
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*

@QuarkusTest
@DisplayName("Create users endpoint")
@ExtendWith(MockitoExtension::class)
class CreateUserResourceTest {
    @InjectMock
    private lateinit var csrfTokenGeneratorIn: CsrfTokenGeneratorIn

    @InjectMock
    private lateinit var cookieUtils: CookieUtils

    @Inject
    private lateinit var jwtTokenGenerator: JwtTokenGenerator

    private val usersDtoMappers: UsersDtoMappers = Mockito.mock(UsersDtoMappers::class.java)

    @InjectMock
    private lateinit var createUsersIn: CreateUsersIn

    companion object {
        val adminRequest: CreateAdminRequest = CreateAdminRequest(
            "Sid",
            "Bennaceur",
            "test@test.com",
            "123S0leil!",
            "0613511351",
            "123456789"
        )
        val mappedRequest: CreateUserCommand = CreateUserCommand(
            adminRequest.firstName,
            adminRequest.lastName,
            adminRequest.mail,
            adminRequest.password,
            adminRequest.phoneNumber,
            null,
            null,
            null,
            null
        )
        private val mapper = ObjectMapper()
        private val json: String = mapper.writeValueAsString(adminRequest)
    }

    @Test
    @DisplayName("Should create admin")
    fun testCreateAdminOkRequest() {
        val csrfToken = "ATOKEN"
        val jwtToken = jwtTokenGenerator.getToken(adminRequest.mail, UserTypes.ADMIN.name)
        val userBasicInformations = UserBasicInformations(
            UserTypes.ADMIN.name,
            "ABCDEF",
            jwtToken,
            false
        )
        val createUserCommandCaptor = argumentCaptor<CreateUserCommand>()
        val adminCodeCaptor = argumentCaptor<String>()

        whenever(usersDtoMappers.fromCreationRequest(adminRequest)).thenReturn(mappedRequest)
        whenever(
            createUsersIn.createAdmin(
                any(), eq(adminRequest.adminCode)
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
            .post("/users-create/admin")
            .then().extract()

        verify(createUsersIn).createAdmin(createUserCommandCaptor.capture(), adminCodeCaptor.capture())

        commonAsserts(createUserCommandCaptor, adminCodeCaptor)
        val responseBody = res.body().`as`(CreateUserResponse::class.java)
        assertTrue(responseBody.type == UserTypes.ADMIN.name)
        assertTrue(responseBody.reference == userBasicInformations.reference)
        assertEquals(res.statusCode(), 200)
        assertEquals(res.cookie("csrf-token"), csrfToken)
        assertEquals(res.cookie("Bearer"), jwtToken)

    }

    @Test
    @DisplayName("Should get bad request")
    fun testCreateAdminBadRequest() {
        val createUserCommandCaptor = argumentCaptor<CreateUserCommand>()
        val adminCodeCaptor = argumentCaptor<String>()

        whenever(usersDtoMappers.fromCreationRequest(adminRequest)).thenReturn(mappedRequest)
        whenever(
            createUsersIn.createAdmin(
                any(), eq(adminRequest.adminCode)
            )
        ).thenThrow(ApplicationException(ApplicationExceptionsEnum.CREATE_USER_INVALID_PHONE_NUMBER))

        val res = RestAssured.given()
            .header("Content-Type", "application/json")
            .body(json)
            .`when`()
            .post("/users-create/admin")
            .then().extract()

        verify(createUsersIn).createAdmin(createUserCommandCaptor.capture(), adminCodeCaptor.capture())

        commonAsserts(createUserCommandCaptor, adminCodeCaptor)
        assertEquals(res.statusCode(), 400)
        val responseBody = res.body().`as`(ApplicationException::class.java)
        assertTrue(responseBody.origin == ApplicationExceptionsEnum.CREATE_USER_INVALID_PHONE_NUMBER.origin)
        assertTrue(responseBody.message == ApplicationExceptionsEnum.CREATE_USER_INVALID_PHONE_NUMBER.message)
    }

    private fun commonAsserts(
        createUserCommandCaptor: KArgumentCaptor<CreateUserCommand>,
        adminCodeCaptor: KArgumentCaptor<String>
    ) {
        assertTrue(createUserCommandCaptor.firstValue.mail == adminRequest.mail)
        assertTrue(createUserCommandCaptor.firstValue.firstName == adminRequest.firstName)
        assertTrue(createUserCommandCaptor.firstValue.lastName == adminRequest.lastName)
        assertTrue(createUserCommandCaptor.firstValue.password == adminRequest.password)
        assertTrue(createUserCommandCaptor.firstValue.phoneNumber == adminRequest.phoneNumber)
        assertTrue(adminCodeCaptor.firstValue == adminRequest.adminCode)
    }


}