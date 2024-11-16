package com.pretnders.pretnders.application.controllers

import com.pretnders.pretnders.application.dto.responses.FindDetailsOptionsResponse
import com.pretnders.pretnders.domain.models.details_enums.*
import jakarta.annotation.security.RolesAllowed
import jakarta.enterprise.context.RequestScoped
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.jboss.resteasy.reactive.ResponseStatus
import org.jboss.resteasy.reactive.RestResponse.StatusCode.OK

@Path("/pretnder-details-options")
@RequestScoped
class QueryDetailOptionsEndpoint {

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(OK)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "200", description = "Find all pretnder details options",
            content = [Content(mediaType = "text/plain",
                schema = Schema(implementation = FindDetailsOptionsResponse::class)
            )]),
    )
    fun findAllOptions(): FindDetailsOptionsResponse {
        val options = FindDetailsOptionsResponse(
            Beliefs.getOptions(),
            BodyTypes.getOptions(),
            Diets.getOptions(),
            SocialStatus.getOptions(),
            Occurrences.getOptions(),
            Genders.getOptions(),
            SexualOrientations.getOptions()
        )
        return options
    }
}