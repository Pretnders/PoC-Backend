package com.pretnders.application.controllers

import com.pretnders.application.dto.requests.ChangeBiographyRequest
import com.pretnders.application.dto.requests.UpdateNicknameRequest
import com.pretnders.application.utils.CookieUtils
import com.pretnders.domain.models.pretnders_details.*
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
class UpdatePretnderDetailsResource {

    @Inject
    private lateinit var jwt:JsonWebToken

    @Inject
    @field:Default
    private lateinit var csrfTokenGeneratorIn: CsrfTokenGeneratorIn

    @field:ConfigProperty(name="quarkus.rest.csrf.cookie.name")
    private lateinit var csrfCookieName: String

    @Inject
    @field: Default
    private lateinit var cookieUtils: CookieUtils

    @Inject
    private lateinit var updatePretnderProfileIn: UpdatePretnderProfileIn

    @Inject
    private lateinit var findNicknameOut: FindNicknameOut


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
        updatePretnderProfileIn.changeNickname(reference, updateNicknameRequest.newNickname)
        return Response.noContent().header("x-csrf-token", csrfToken).cookie(csrfCookie).build()
    }

    @PUT
    @Path("/height/{detailsReference}/{newHeight}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun update(@PathParam("detailsReference") detailsReference:String, @PathParam
        ("newHeight") newHeight:String): Response {
        val mail = jwt.claim<String>(Claims.email.name).get()
        val csrfToken = csrfTokenGeneratorIn.generateToken(mail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        updatePretnderProfileIn.changeHeight(detailsReference, newHeight)
        return Response.noContent().header("x-csrf-token", csrfToken).cookie(csrfCookie).build()
    }

    @PUT
    @Path("/body-type/{detailsReference}/{newBodyType}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun update(@PathParam("detailsReference") detailsReference:String, @PathParam
        ("newBodyType") newBodyType:BodyTypes): Response {
        val mail = jwt.claim<String>(Claims.email.name).get()
        val csrfToken = csrfTokenGeneratorIn.generateToken(mail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        updatePretnderProfileIn.changeBodyType(detailsReference, newBodyType)
        return Response.noContent().header("x-csrf-token", csrfToken).cookie(csrfCookie).build()
    }

    @PUT
    @Path("/diet/{detailsReference}/{newDiet}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun update(@PathParam("detailsReference") detailsReference:String, @PathParam
        ("newDiet") newDiet:Diets): Response {
        val mail = jwt.claim<String>(Claims.email.name).get()
        val csrfToken = csrfTokenGeneratorIn.generateToken(mail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        updatePretnderProfileIn.changeDiet(detailsReference, newDiet)
        return Response.noContent().header("x-csrf-token", csrfToken).cookie(csrfCookie).build()
    }

    @PUT
    @Path("/beliefs/{detailsReference}/{newBelief}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun update(@PathParam("detailsReference") detailsReference:String, @PathParam
        ("newBelief") newBelief:Beliefs): Response {
        val mail = jwt.claim<String>(Claims.email.name).get()
        val csrfToken = csrfTokenGeneratorIn.generateToken(mail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        updatePretnderProfileIn.changeBeliefs(detailsReference, newBelief)
        return Response.noContent().header("x-csrf-token", csrfToken).cookie(csrfCookie).build()
    }

    @PUT
    @Path("/smokes/{detailsReference}/{newOccurrence}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun updateSmokes(@PathParam("detailsReference") detailsReference:String, @PathParam
        ("newOccurrence") newOccurrence:Occurrences): Response {
        val mail = jwt.claim<String>(Claims.email.name).get()
        val csrfToken = csrfTokenGeneratorIn.generateToken(mail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        updatePretnderProfileIn.changeSmokes(detailsReference, newOccurrence)
        return Response.noContent().header("x-csrf-token", csrfToken).cookie(csrfCookie).build()
    }

    @PUT
    @Path("/drinks/{detailsReference}/{newOccurrence}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun updateDrinks(@PathParam("detailsReference") detailsReference:String, @PathParam
        ("newOccurrence") newOccurrence:Occurrences): Response {
        val mail = jwt.claim<String>(Claims.email.name).get()
        val csrfToken = csrfTokenGeneratorIn.generateToken(mail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        updatePretnderProfileIn.changeDrinks(detailsReference, newOccurrence)
        return Response.noContent().header("x-csrf-token", csrfToken).cookie(csrfCookie).build()
    }

    @PUT
    @Path("/social-status/{detailsReference}/{newSocialStatus}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun update(@PathParam("detailsReference") detailsReference:String, @PathParam
        ("newSocialStatus") newSocialStatus:SocialStatus): Response {
        val mail = jwt.claim<String>(Claims.email.name).get()
        val csrfToken = csrfTokenGeneratorIn.generateToken(mail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        updatePretnderProfileIn.changeSocialStatus(detailsReference, newSocialStatus)
        return Response.noContent().header("x-csrf-token", csrfToken).cookie(csrfCookie).build()
    }

    @PUT
    @Path("/biography")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun update(changeBiographyRequest: ChangeBiographyRequest): Response {
        val mail = jwt.claim<String>(Claims.email.name).get()
        val csrfToken = csrfTokenGeneratorIn.generateToken(mail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        updatePretnderProfileIn.changeBiography(changeBiographyRequest.reference, changeBiographyRequest.biography)
        return Response.noContent().header("x-csrf-token", csrfToken).cookie(csrfCookie).build()
    }

    @PUT
    @Path("/city/{detailsReference}/{newCity}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun updateCity(@PathParam("detailsReference") detailsReference:String, @PathParam("newCity") newCity:String): Response {
        val mail = jwt.claim<String>(Claims.email.name).get()
        val csrfToken = csrfTokenGeneratorIn.generateToken(mail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        updatePretnderProfileIn.changeCity(detailsReference, newCity)
        return Response.noContent().header("x-csrf-token", csrfToken).cookie(csrfCookie).build()
    }

    @PUT
    @Path("/country/{detailsReference}/{newCountry}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun updateCountry(@PathParam("detailsReference") detailsReference:String, @PathParam("newCountry") newCountry:String):
            Response {
        val mail = jwt.claim<String>(Claims.email.name).get()
        val csrfToken = csrfTokenGeneratorIn.generateToken(mail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        updatePretnderProfileIn.changeCountry(detailsReference, newCountry)
        return Response.noContent().header("x-csrf-token", csrfToken).cookie(csrfCookie).build()
    }

    @PUT
    @Path("/orientation/{detailsReference}/{newOrientation}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun update(@PathParam("detailsReference") detailsReference:String, @PathParam
        ("newOrientation") newOrientation:SexualOrientations): Response {
        val mail = jwt.claim<String>(Claims.email.name).get()
        val csrfToken = csrfTokenGeneratorIn.generateToken(mail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        updatePretnderProfileIn.changeOrientation(detailsReference, newOrientation)
        return Response.noContent().header("x-csrf-token", csrfToken).cookie(csrfCookie).build()
    }

    @PUT
    @Path("/gender/{detailsReference}/{newGender}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun update(@PathParam("detailsReference") detailsReference:String, @PathParam
        ("newGender") newGender:Genders): Response {
        val mail = jwt.claim<String>(Claims.email.name).get()
        val csrfToken = csrfTokenGeneratorIn.generateToken(mail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        updatePretnderProfileIn.changeGender(detailsReference, newGender)
        return Response.noContent().header("x-csrf-token", csrfToken).cookie(csrfCookie).build()
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
    fun find(@PathParam("desiredNickname") desiredNickname:String): Response? {
        val reference = jwt.name
        val mail = jwt.claim<String>(Claims.email.name).get()
        val csrfToken = csrfTokenGeneratorIn.generateToken(mail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        return Response.ok(findNicknameOut.doNicknameExist(reference, desiredNickname)).header("x-csrf-token", csrfToken).cookie(csrfCookie).build()
    }
}