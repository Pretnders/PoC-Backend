package com.pretnders.application.controllers

import com.pretnders.application.dto.requests.CreateAdminRequest
import com.pretnders.application.dto.responses.CreateAccountResponse
import com.pretnders.application.mappers.AdminDtoMapper
import com.pretnders.domain.ports.`in`.CreateAdminIn
import com.pretnders.domain.ports.`in`.CsrfTokenGeneratorIn
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

@Path("/admin-creation")
@RequestScoped
class AdminCreationResource {

    @Inject
    @field: Default
    private lateinit var createAdminIn: CreateAdminIn

    @Inject
    @field: Default
    private lateinit var cookieUtils: CookieUtils

    @Inject
    @field: Default
    private lateinit var adminDtoMapper: AdminDtoMapper

    @Inject
    @field:Default
    private lateinit var csrfTokenGeneratorIn: CsrfTokenGeneratorIn

    @field:ConfigProperty(name="quarkus.rest-csrf.cookie-name")
    private lateinit var csrfCookieName: String

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(CREATED)
    @PermitAll
    @Operation(summary = "Create an admin", description = "Create an admin")
    @APIResponses(
        APIResponse(responseCode = "200", description = "OK", content = [Content(mediaType = "application/json",
            schema = Schema(implementation = CreateAccountResponse::class)
        )]),
    )
    fun createPretender(creationRequest: CreateAdminRequest): Response {
        Log.info("Creating client")
        val mappedRequest = adminDtoMapper.fromCreationRequest(creationRequest)
        Log.debug("Creating admin ${mappedRequest.nickname}")
        val userCreationInformations = createAdminIn.createAdmin(mappedRequest)
        val bearerCookie = cookieUtils.setUpCookie("Bearer", userCreationInformations.jwToken)
        val csrfToken = csrfTokenGeneratorIn.generateToken(mappedRequest.mail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        return Response.ok().cookie(bearerCookie).cookie(csrfCookie).build()
    }
}