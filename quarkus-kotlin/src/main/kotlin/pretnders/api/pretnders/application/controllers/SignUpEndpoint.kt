package pretnders.api.pretnders.application.controllers

import io.quarkus.logging.Log
import jakarta.annotation.security.PermitAll
import jakarta.enterprise.context.RequestScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.jboss.resteasy.reactive.ResponseStatus
import org.jboss.resteasy.reactive.RestResponse.StatusCode.CREATED
import pretnders.api.pretnders.application.dto.requests.AddPretenderRequest
import pretnders.api.pretnders.application.mappers.PretndersDtoMappers
import pretnders.api.pretnders.domain.ports.`in`.AddPretndersIn
import pretnders.api.shared.security.CookieUtils


@Path("/create-pretnder")
@RequestScoped
class SignUpEndpoint (
    private val addPretndersIn: AddPretndersIn,
    private val cookieUtils: CookieUtils,
    private val pretndersDtoMappers: PretndersDtoMappers
) {


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(CREATED)
    @PermitAll
    @Operation(summary = "Create a pretnder", description = "Create a pretnder")
    @APIResponses(
        APIResponse(responseCode = "201", description = "Created"),
    )
    fun create(creationRequest: AddPretenderRequest): Response {
        Log.info("Creating client")
        val mappedRequest = pretndersDtoMappers.fromCreationRequest(creationRequest)
        Log.debug(String.format("Creating user %s %s", mappedRequest.firstName, mappedRequest.lastName))
        val userCreationInformations = addPretndersIn.createPretender(mappedRequest)
        val bearerCookie = cookieUtils.setUpCookie("Bearer", userCreationInformations.jwToken)
        return Response.status(Response.Status.CREATED).cookie(bearerCookie).build()
    }

}