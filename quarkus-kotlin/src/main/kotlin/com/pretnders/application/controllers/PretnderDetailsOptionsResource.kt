package com.pretnders.application.controllers

import com.pretnders.application.dto.responses.PretnderDetailsOptionsResponse
import com.pretnders.application.utils.CookieUtils
import com.pretnders.domain.models.pretnders_details.*
import com.pretnders.domain.ports.`in`.CsrfTokenGeneratorIn
import jakarta.annotation.security.RolesAllowed
import jakarta.enterprise.context.RequestScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.jwt.Claims
import org.eclipse.microprofile.jwt.JsonWebToken
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.jboss.resteasy.reactive.ResponseStatus
import org.jboss.resteasy.reactive.RestResponse.StatusCode.NO_CONTENT

@Path("/pretnder-details-options")
@RequestScoped
class PretnderDetailsOptionsResource {
    @Inject
    private lateinit var jwt: JsonWebToken

    @Inject
    @field:Default
    private lateinit var csrfTokenGeneratorIn: CsrfTokenGeneratorIn

    @field:ConfigProperty(name="quarkus.rest.csrf.cookie.name")
    private lateinit var csrfCookieName: String

    @Inject
    @field: Default
    private lateinit var cookieUtils: CookieUtils

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "200", description = "Find all pretnder details options",
            content = [Content(mediaType = "text/plain",
                schema = Schema(implementation = PretnderDetailsOptionsResponse::class)
            )]),
    )
    fun findAllOptions(): Response? {
        val mail = jwt.claim<String>(Claims.email.name).get()
        val csrfToken = csrfTokenGeneratorIn.generateToken(mail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        val options = PretnderDetailsOptionsResponse(
            Beliefs.getOptions(),
            BodyTypes.getOptions(),
            Diets.getOptions(),
            SocialStatus.getOptions(),
            Occurrences.getOptions(),
            Genders.getOptions(),
            SexualOrientations.getOptions()
        )
        return Response.ok(options).header("x-csrf-token", csrfToken).cookie(csrfCookie).build()
    }
}