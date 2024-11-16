package com.pretnders.pretnders.application.controllers

import com.pretnders.pretnders.application.dto.requests.AddPretenderRequest
import com.pretnders.pretnders.application.mappers.PretndersDtoMappers
import com.pretnders.shared.security.CookieUtils
import com.pretnders.pretnders.domain.ports.`in`.CreatePretendersIn
import com.pretnders.shared.security.CsrfTokenGeneratorIn
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
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.jboss.resteasy.reactive.ResponseStatus
import org.jboss.resteasy.reactive.RestResponse.StatusCode.CREATED


@Path("/create-pretnder")
@RequestScoped
class SignUpEndpoint {

    @Inject
    @field: Default
    private lateinit var createPretendersIn: CreatePretendersIn

    @Inject
    @field: Default
    private lateinit var cookieUtils: CookieUtils

    @Inject
    @field: Default
    private lateinit var pretndersDtoMappers: PretndersDtoMappers

    @Inject
    @field:Default
    private lateinit var csrfTokenGeneratorIn: CsrfTokenGeneratorIn

    @field:ConfigProperty(name="quarkus.rest.csrf.cookie.name")
    private lateinit var csrfCookieName: String

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(CREATED)
    @PermitAll
    @Operation(summary = "Create a pretnder", description = "Create a pretnder")
    @APIResponses(
        APIResponse(responseCode = "201", description = "Created"),
    )
    fun create(creationRequest: AddPretenderRequest): Response {
        Log.info("Creating client")
        val mappedRequest = pretndersDtoMappers.fromCreationRequest(creationRequest)
        Log.debug(String.format("Creating user %s %s", mappedRequest.firstName, mappedRequest.lastName))
        val userCreationInformations = createPretendersIn.createPretender(mappedRequest)
        val bearerCookie = cookieUtils.setUpCookie("Bearer", userCreationInformations.jwToken)
        return Response.status(Response.Status.CREATED).cookie(bearerCookie).build()
    }

}