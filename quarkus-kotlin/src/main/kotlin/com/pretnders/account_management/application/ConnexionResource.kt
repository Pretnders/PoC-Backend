package com.pretnders.account_management.application

import com.pretnders.account_management.domain.LoginIn
import com.pretnders.pretnders.application.dto.responses.LoginResponse
import com.pretnders.admins.application.AdminDtoMapper
import com.pretnders.pretnders.application.mappers.PretndersDtoMappers
import com.pretnders.shared.security.CookieUtils
import com.pretnders.shared.security.CsrfTokenGeneratorIn
import io.quarkus.logging.Log
import jakarta.annotation.security.PermitAll
import jakarta.annotation.security.RolesAllowed
import jakarta.enterprise.context.RequestScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.jboss.resteasy.reactive.ResponseStatus
import org.jboss.resteasy.reactive.RestResponse.StatusCode.OK


@Path("/login")
@RequestScoped
class ConnexionResource {
    @Inject
    @field:Default
    private lateinit var loginIn: LoginIn
    @Inject
    @field:Default
    private lateinit var pretndersDtoMappers: PretndersDtoMappers
    @Inject
    @field:Default
    private lateinit var adminDtoMapper: AdminDtoMapper
    @Inject
    @field:Default
    private lateinit var csrfTokenGeneratorIn: CsrfTokenGeneratorIn
    
    @field:ConfigProperty(name="quarkus.rest.csrf.cookie.name")
    private lateinit var csrfCookieName: String
    @Inject
    @field: Default
    private lateinit var cookieUtils: CookieUtils

    @POST
    @Path("/admin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ResponseStatus(OK)
    @PermitAll
    @Operation(summary = "Logs an admin", description = "Logs in an admin")
    @APIResponses(
        APIResponse(responseCode = "200", description = "OK", content = [Content(mediaType = "application/json",
            schema = Schema(implementation = LoginResponse::class)
        )]),
    )
    fun adminLogin(loginRequest: LoginRequest): Response {
        Log.debug(String.format("Logging admin %s", loginRequest.identifier))
        val loggedIn = loginIn.loginAdmin(loginRequest.identifier, loginRequest.password)
        val bearerCookie = cookieUtils.setUpCookie("Bearer", loggedIn.jwToken)
       return  Response.ok(adminDtoMapper.toAdminResponse(loggedIn)).cookie(bearerCookie).build()
    }

    @POST
    @Path("/pretnder")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ResponseStatus(OK)
    @PermitAll
    @Operation(summary = "Logs a pretnder", description = "Logs in a pretnder")
    @APIResponses(
        APIResponse(responseCode = "200", description = "OK", content = [Content(mediaType = "application/json",
            schema = Schema(implementation = LoginResponse::class)
        )]),
    )
    fun pretenderLogin(loginRequest: LoginRequest): Response {
        Log.debug(String.format("Logging user %s", loginRequest.identifier))
        val loggedIn = loginIn.loginPretnder(loginRequest.identifier, loginRequest.password)
        val bearerCookie = cookieUtils.setUpCookie("Bearer", loggedIn.jwToken)
        return  Response.ok(pretndersDtoMappers.toLoginResponse(loggedIn)).cookie(bearerCookie).build()
    }

    @POST
    @Path("/logout")
    @ResponseStatus(OK)
    @RolesAllowed("PRETNDER")
    fun logout(): Response {
        val cookie = cookieUtils.setUpCookie("Bearer", "")
        return Response.ok("Logged out").cookie(cookie).build()
    }
}