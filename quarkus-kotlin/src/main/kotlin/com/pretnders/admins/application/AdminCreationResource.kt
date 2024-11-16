package com.pretnders.admins.application

import com.pretnders.admins.domain.CreateAdminIn
import com.pretnders.shared.security.CookieUtils
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
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.jboss.resteasy.reactive.ResponseStatus
import org.jboss.resteasy.reactive.RestResponse.StatusCode.NO_CONTENT

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


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @PermitAll
    @Operation(summary = "Create an admin", description = "Create an admin")
    @APIResponses(
        APIResponse(responseCode = "200", description = "OK")
    )
    fun create(creationRequest: CreateAdminRequest): Response {
        Log.info("Creating client")
        val mappedRequest = adminDtoMapper.fromCreationRequest(creationRequest)
        Log.debug("Creating admin ${mappedRequest.nickname}")
        val userCreationInformations = createAdminIn.createAdmin(mappedRequest)
        val bearerCookie = cookieUtils.setUpCookie("Bearer", userCreationInformations.jwToken)
        return Response.status(Response.Status.CREATED).cookie(bearerCookie).build()
    }
}