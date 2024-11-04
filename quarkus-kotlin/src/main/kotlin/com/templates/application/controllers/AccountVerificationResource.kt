package com.templates.application.controllers

import com.templates.application.dto.requests.OtpRequest
import com.templates.domain.ports.`in`.CsrfTokenGeneratorIn
import com.templates.domain.ports.`in`.VerifyAccountsIn
import jakarta.annotation.security.RolesAllowed
import jakarta.enterprise.context.RequestScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.jwt.JsonWebToken
import io.quarkus.logging.Log;
import org.jboss.resteasy.reactive.ResponseStatus
import org.jboss.resteasy.reactive.RestResponse.StatusCode.NO_CONTENT

@Path("/verify-account")
@RequestScoped
class AccountVerificationResource {

    @Inject
    @field: Default
    private lateinit var cookieUtils: CookieUtils

    @Inject
    @field:Default
    private lateinit var verifyAccountsIn: VerifyAccountsIn

    @Inject
    @field:Default
    private lateinit var jwt: JsonWebToken

    @Inject
    @field:Default
    private lateinit var csrfTokenGeneratorIn: CsrfTokenGeneratorIn

    @field:ConfigProperty(name="quarkus.rest-csrf.cookie-name")
    private lateinit var csrfCookieName: String

    @PUT
    @Path("/client")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("CLIENT")
    fun verifyClientAccount(otpRequest: OtpRequest):Response {
        Log.info("Vérifying user account")
        val mail = jwt.name
        verifyAccountsIn.verifyClientAccount(mail, otpRequest.otpCode)
        val csrfToken = csrfTokenGeneratorIn.generateToken(mail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        return Response.noContent().cookie(csrfCookie).build()
    }

    @PUT
    @Path("/new-otp")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("CLIENT")
    fun generateNewOtpCode():Response {
        Log.info("Initiating new OTP")

        val mail = jwt.name
        verifyAccountsIn.generateNewOtpCode(mail)
        val csrfToken = csrfTokenGeneratorIn.generateToken(mail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        return Response.noContent().cookie(csrfCookie).build()
    }
}