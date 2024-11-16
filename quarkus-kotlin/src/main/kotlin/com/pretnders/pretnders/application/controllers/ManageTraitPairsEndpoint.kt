package com.pretnders.pretnders.application.controllers

import com.pretnders.pretnders.application.dto.requests.ChangeTraitScoreRequest
import com.pretnders.shared.security.CookieUtils
import com.pretnders.shared.security.CsrfTokenGeneratorIn
import com.pretnders.pretnders.domain.ports.`in`.ChangeTraitScoreIn
import io.quarkus.logging.Log
import jakarta.annotation.security.RolesAllowed
import jakarta.enterprise.context.RequestScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.jwt.JsonWebToken
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.jboss.resteasy.reactive.ResponseStatus
import org.jboss.resteasy.reactive.RestResponse.StatusCode.NO_CONTENT

@Path("/pretnder-traits")
@RequestScoped
class ManageTraitPairsEndpoint {

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

    @Inject
    private lateinit var changeTraitScoreIn: ChangeTraitScoreIn
    @PUT
    @Path("/update-score")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun changeNickname(changeTraitScoreRequest: ChangeTraitScoreRequest) {
        Log.info("Updating score for trait pair ${changeTraitScoreRequest.traitPairReference} with new score " +
                "${changeTraitScoreRequest.score}")
        changeTraitScoreIn.updateScore(changeTraitScoreRequest.traitPairReference, changeTraitScoreRequest.score)
    }
}