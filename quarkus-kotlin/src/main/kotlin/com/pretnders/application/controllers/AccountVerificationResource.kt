package com.pretnders.application.controllers

import com.pretnders.application.dto.requests.OtpRequest
import com.pretnders.domain.ports.`in`.CsrfTokenGeneratorIn
import com.pretnders.domain.ports.`in`.VerifyAccountsIn
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
import io.quarkus.logging.Log
import org.eclipse.microprofile.jwt.Claims
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
    private lateinit var jwt: JsonWebToken

    @Inject
    private lateinit var csrfTokenGeneratorIn: CsrfTokenGeneratorIn

    @field:ConfigProperty(name="quarkus.rest-csrf.cookie-name")
    private lateinit var csrfCookieName: String

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    fun verifyClientAccount(otpRequest: OtpRequest):Response {
        Log.info("Vérifying user account")
        val mail = jwt.claim<String>(Claims.email.name).get()
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

        val mail = jwt.claim<String>(Claims.email.name).get()
        verifyAccountsIn.generateNewOtpCode(mail)
        val csrfToken = csrfTokenGeneratorIn.generateToken(mail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        return Response.noContent().cookie(csrfCookie).build()
    }
}