package com.pretnders.application.controllers

import com.pretnders.application.utils.CookieUtils
import com.pretnders.domain.ports.`in`.CsrfTokenGeneratorIn
import jakarta.annotation.security.RolesAllowed
import jakarta.enterprise.context.RequestScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.jwt.Claims
import org.eclipse.microprofile.jwt.JsonWebToken
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement
import org.jboss.resteasy.reactive.ResponseStatus
import org.jboss.resteasy.reactive.RestResponse.StatusCode.ACCEPTED

@Path("/csrf-token")
@RequestScoped
class CsrfTokenResource {
    @Inject
    @field:Default
    private lateinit var jwt: JsonWebToken
    @Inject
    @field:Default
    private lateinit var csrfTokenGeneratorIn: CsrfTokenGeneratorIn
    @Inject
    @field: Default
    private lateinit var cookieUtils: CookieUtils
    @GET
    @ResponseStatus(ACCEPTED)
    @RolesAllowed("PRETNDER","ADMIN")
    @SecurityRequirement(name = "bearer")
    fun getCsrfToken( ): Response {
        val userMail = jwt.claim<String>(Claims.email.name).get()
        val csrfToken = csrfTokenGeneratorIn.generateToken(userMail)
        val cookie = cookieUtils.setUpCookie("csrf-token", csrfToken)
        return Response.ok().cookie(cookie).build()
    }
}