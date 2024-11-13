package com.pretnders.application.controllers

import com.pretnders.application.dto.requests.UpdateNicknameRequest
import com.pretnders.application.utils.CookieUtils
import com.pretnders.domain.models.pretnders_details.SexualOrientation
import com.pretnders.domain.ports.`in`.CsrfTokenGeneratorIn
import com.pretnders.domain.ports.`in`.UpdatePretnderProfileIn
import com.pretnders.domain.ports.out.FindNicknameOut
import jakarta.annotation.security.RolesAllowed
import jakarta.enterprise.context.RequestScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
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

@Path("/pretnder-profile")
@RequestScoped
class PretnderDetailsResource {

    @Inject
    private lateinit var jwt:JsonWebToken

    @Inject
    private lateinit var updatePretnderProfileIn: UpdatePretnderProfileIn

    @Inject
    private lateinit var findNicknameOut: FindNicknameOut

    @Inject
    @field:Default
    private lateinit var csrfTokenGeneratorIn: CsrfTokenGeneratorIn

    @field:ConfigProperty(name="quarkus.rest-csrf.cookie-name")
    private lateinit var csrfCookieName: String

    @Inject
    @field: Default
    private lateinit var cookieUtils: CookieUtils

    @PUT
    @Path("/nickname")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun changeNickname(updateNicknameRequest: UpdateNicknameRequest): Response {
        val reference = jwt.name
        val mail = jwt.claim<String>(Claims.email.name).get()
        val csrfToken = csrfTokenGeneratorIn.generateToken(mail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        updatePretnderProfileIn.updateNickname(reference, updateNicknameRequest.newNickname)
        return Response.noContent().cookie(csrfCookie).build()
    }

    @PUT
    @Path("/orientation/{detailsReference}/{newOrientation}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun changeSexualOrientation(@PathParam("detailsReference") detailsReference:String, @PathParam
        ("newOrientation") newOrientation:SexualOrientation): Response {
        val mail = jwt.claim<String>(Claims.email.name).get()
        val csrfToken = csrfTokenGeneratorIn.generateToken(mail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        updatePretnderProfileIn.updateOrientation(detailsReference, newOrientation)
        return Response.noContent().cookie(csrfCookie).build()
    }

    @GET
    @Path("/nickname/{desiredNickname}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "200", description = "Boolean representing the nickname existence in database",
            content = [Content(mediaType = "text/plain",
            schema = Schema(implementation = Boolean::class)
        )]),
    )
    fun doNicknameExist(@PathParam("desiredNickname") desiredNickname:String): Response? {
        val reference = jwt.name
        val mail = jwt.claim<String>(Claims.email.name).get()
        val csrfToken = csrfTokenGeneratorIn.generateToken(mail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        return Response.ok(findNicknameOut.doNicknameExist(reference, desiredNickname)).cookie(csrfCookie).build()
    }
}