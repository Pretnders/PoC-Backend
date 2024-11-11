package com.pretnders.application.controllers

import com.pretnders.domain.ports.`in`.AdminCodeIn
import com.pretnders.domain.ports.`in`.CsrfTokenGeneratorIn
import io.quarkus.logging.Log
import jakarta.annotation.security.PermitAll
import jakarta.enterprise.context.RequestScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.jwt.JsonWebToken
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.jboss.resteasy.reactive.ResponseStatus
import org.jboss.resteasy.reactive.RestResponse.StatusCode.OK


@Path("/admin-code")
@RequestScoped
class AdminCodeResource {

    @Inject
    @field:Default
    private lateinit var adminCodeIn: AdminCodeIn

    @Inject
    @field:Default
    private lateinit var jwt: JsonWebToken

    @Inject
    @field:Default
    private lateinit var csrfTokenGeneratorIn: CsrfTokenGeneratorIn

    @field:ConfigProperty(name="quarkus.rest-csrf.cookie-name")
    private lateinit var csrfCookieName: String

    @Inject
    @field: Default
    private lateinit var cookieUtils: CookieUtils

    @GET
    @ResponseStatus(OK)
    @APIResponses(
        APIResponse(responseCode = "200", description = "OK", content = [Content(mediaType = "text/plain",
            schema = Schema(implementation = String::class))]),
    )
    @PermitAll
    fun getAdminCode(): Response {
        Log.info("Retrieving new admin code")
//        val mail = jwt.claim<String>(Claims.email.name).get()
//        val csrfToken = csrfTokenGeneratorIn.generateToken(mail)
  //      val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        return Response.ok(adminCodeIn.getCurrentCode())
            //.cookie(csrfCookie)
            .build()
    }
}