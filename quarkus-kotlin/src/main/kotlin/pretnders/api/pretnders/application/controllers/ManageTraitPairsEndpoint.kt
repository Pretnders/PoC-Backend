package pretnders.api.pretnders.application.controllers

import pretnders.api.pretnders.application.dto.requests.ChangeTraitScoreRequest
import pretnders.api.shared.security.CookieUtils
import pretnders.api.shared.security.CsrfTokenGeneratorIn
import pretnders.api.pretnders.domain.ports.`in`.ChangeTraitScoreIn
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
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.jboss.resteasy.reactive.ResponseStatus
import org.jboss.resteasy.reactive.RestResponse.StatusCode.NO_CONTENT

@Path("/pretnder-traits")
@RequestScoped
class ManageTraitPairsEndpoint (
    private val changeTraitScoreIn: ChangeTraitScoreIn
) {

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun changeTraitPairScore(changeTraitScoreRequest: ChangeTraitScoreRequest) {
        Log.info("Updating score for trait pair ${changeTraitScoreRequest.traitPairReference} with new score " +
                "${changeTraitScoreRequest.score}")
        changeTraitScoreIn.updateScore(changeTraitScoreRequest.traitPairReference, changeTraitScoreRequest.score)
    }
}