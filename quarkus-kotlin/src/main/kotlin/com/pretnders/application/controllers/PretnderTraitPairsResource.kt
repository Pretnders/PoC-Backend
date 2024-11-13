package com.pretnders.application.controllers

import com.pretnders.application.dto.requests.UpdateNicknameRequest
import com.pretnders.application.dto.traitpairs.UpdateTraitScoreRequest
import com.pretnders.application.utils.CookieUtils
import com.pretnders.domain.ports.`in`.CsrfTokenGeneratorIn
import com.pretnders.domain.ports.`in`.UpdatePretnderTraitPairIn
import io.quarkus.logging.Log
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
import org.eclipse.microprofile.jwt.Claims
import org.eclipse.microprofile.jwt.JsonWebToken
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.jboss.resteasy.reactive.ResponseStatus
import org.jboss.resteasy.reactive.RestResponse.StatusCode.NO_CONTENT

@Path("/pretnder-traits")
@RequestScoped
class PretnderTraitPairsResource {

    @Inject
    private lateinit var jwt: JsonWebToken
    @Inject
    @field:Default
    private lateinit var csrfTokenGeneratorIn: CsrfTokenGeneratorIn

    @field:ConfigProperty(name="quarkus.rest-csrf.cookie-name")
    private lateinit var csrfCookieName: String

    @Inject
    @field: Default
    private lateinit var cookieUtils: CookieUtils

    @Inject
    private lateinit var updatePretnderTraitPairIn: UpdatePretnderTraitPairIn
    @PUT
    @Path("/update-score")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun changeNickname(updateTraitScoreRequest: UpdateTraitScoreRequest): Response {
        Log.info("Updating score for trait pair ${updateTraitScoreRequest.traitPairReference} with new score " +
                "${updateTraitScoreRequest.score}")
        val mail = jwt.claim<String>(Claims.email.name).get()
        val csrfToken = csrfTokenGeneratorIn.generateToken(mail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        updatePretnderTraitPairIn.updateScore(updateTraitScoreRequest.traitPairReference, updateTraitScoreRequest.score)
        return Response.noContent().cookie(csrfCookie).build()
    }
}