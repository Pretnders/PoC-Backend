package com.templates.application.controllers

import com.templates.application.dto.requests.CreateAdminRequest
import com.templates.application.dto.requests.CreateUserRequest
import com.templates.application.dto.responses.CreateUserResponse
import com.templates.application.mappers.UsersDtoMappers
import com.templates.domain.ports.`in`.CreateUsersIn
import com.templates.domain.ports.`in`.CsrfTokenGeneratorIn
import io.quarkus.logging.Log
import jakarta.annotation.security.PermitAll
import jakarta.enterprise.context.RequestScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.jboss.resteasy.reactive.ResponseStatus
import org.jboss.resteasy.reactive.RestResponse.StatusCode.CREATED


@Path("/users-create")
@RequestScoped
class CreateUsersResource {

    @Inject
    @field: Default
    private lateinit var createUsersIn: CreateUsersIn

    @Inject
    @field: Default
    private lateinit var cookieUtils: CookieUtils

    @Inject
    @field: Default
    private lateinit var usersDtoMappers: UsersDtoMappers

    @Inject
    @field:Default
    private lateinit var csrfTokenGeneratorIn: CsrfTokenGeneratorIn

    @field:ConfigProperty(name="quarkus.rest-csrf.cookie-name")
    private lateinit var csrfCookieName: String

    @POST
    @Path("/clients")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(CREATED)
    @PermitAll
    @Operation(summary = "Create a client", description = "Create a client")
    @APIResponses(
        APIResponse(responseCode = "200", description = "OK", content = [Content(mediaType = "application/json",
            schema = Schema(implementation = CreateUserResponse::class)
        )]),
    )
    fun createClient(creationRequest: CreateUserRequest): Response {
        Log.info("Creating client")
        val mappedRequest = usersDtoMappers.fromCreationRequest(creationRequest)
        Log.debug(String.format("Creating user %s %s", mappedRequest.firstName, mappedRequest.lastName))
        val userCreationInformations = createUsersIn.createUser(mappedRequest)
        val bearerCookie = cookieUtils.setUpCookie("Bearer", userCreationInformations.jwToken)
        val csrfToken = csrfTokenGeneratorIn.generateToken(mappedRequest.mail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        return Response.ok(usersDtoMappers.toCreationResponse(userCreationInformations)).cookie(bearerCookie).cookie(csrfCookie).build()
    }

    @POST
    @Path("/admin")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(CREATED)
    @PermitAll
    @Operation(summary = "Create an admin", description = "Create an admin")
    @APIResponses(
        APIResponse(responseCode = "200", description = "OK", content = [Content(mediaType = "application/json",
            schema = Schema(implementation = CreateUserResponse::class)
        )]),
    )
    fun createAdmin(creationRequest: CreateAdminRequest): Response {
        Log.info("Creating admin")
        Log.info(creationRequest.toString())
        val mappedRequest = usersDtoMappers.fromCreationRequest(creationRequest)
        Log.info(String.format("Creating admin %s %s", mappedRequest.firstName, mappedRequest.lastName))
        val userCreationInformations = createUsersIn.createAdmin(mappedRequest, creationRequest.adminCode)
        Log.info("Setting cookies")
        val bearerCookie = cookieUtils.setUpCookie("Bearer", userCreationInformations.jwToken)
        val csrfToken = csrfTokenGeneratorIn.generateToken(mappedRequest.mail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        return Response.ok(usersDtoMappers.toCreationResponse(userCreationInformations)).cookie(bearerCookie).cookie(csrfCookie).build()
    }
}