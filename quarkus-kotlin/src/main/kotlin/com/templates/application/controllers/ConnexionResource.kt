package com.templates.application.controllers

import com.templates.application.dto.requests.LoginRequest
import com.templates.application.dto.responses.UserLoginResponse
import com.templates.application.mappers.UsersDtoMappers
import com.templates.domain.ports.`in`.CsrfTokenGeneratorIn
import com.templates.domain.ports.`in`.LoginIn
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


@Path("/connection")
@RequestScoped
class ConnexionResource {
    @Inject
    @field:Default
    private lateinit var loginIn: LoginIn
    @Inject
    @field:Default
    private lateinit var usersDtoMappers: UsersDtoMappers
    @Inject
    @field:Default
    private lateinit var csrfTokenGeneratorIn: CsrfTokenGeneratorIn
    
    @field:ConfigProperty(name="quarkus.rest-csrf.cookie-name")
    private lateinit var csrfCookieName: String
    @Inject
    @field: Default
    private lateinit var cookieUtils: CookieUtils
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ResponseStatus(OK)
    @PermitAll
    @Operation(summary = "Logs a user", description = "Logs in a user")
    @APIResponses(
        APIResponse(responseCode = "200", description = "OK", content = [Content(mediaType = "application/json",
            schema = Schema(implementation = UserLoginResponse::class)
        )]),
    )
    fun login(loginRequest: LoginRequest): Response {
        Log.debug(String.format("Logging user %s", loginRequest.identifier))

        val loggedIn = loginIn.login(loginRequest.identifier, loginRequest.password)
        val bearerCookie = cookieUtils.setUpCookie("Bearer", loggedIn.jwToken)
        val csrfToken = csrfTokenGeneratorIn.generateToken(loggedIn.mail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
       return  Response.ok(usersDtoMappers.toLoginResponse(loggedIn)).cookie(bearerCookie).cookie(csrfCookie).build()
    }

    @POST
    @Path("/logout")
    @RolesAllowed("CLIENT","ADMIN")
    fun logout(): Response {
        val cookie = cookieUtils.setUpCookie("Bearer", "")
        return Response.ok("Logged out").cookie(cookie).build()
    }
}