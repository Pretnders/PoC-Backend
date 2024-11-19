package pretnders.api.pretnders.application.controllers

import jakarta.annotation.security.PermitAll
import jakarta.annotation.security.RolesAllowed
import jakarta.enterprise.context.RequestScoped
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.jwt.JsonWebToken
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.jboss.resteasy.reactive.ResponseStatus
import org.jboss.resteasy.reactive.RestResponse.StatusCode.NO_CONTENT
import pretnders.api.pretnders.domain.ports.out.QueryNicknamesOut

@Path("/pretnder-nickname")
@RequestScoped
class QueryNicknamesEndpoint (
    private val jwt: JsonWebToken,
    private val queryNicknamesOut: QueryNicknamesOut
){
    @GET
    @Path("/new-nickname/{desiredNickname}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "200", description = "Boolean representing the nickname existence in database",
            content = [Content(mediaType = "text/plain",
                schema = Schema(implementation = Boolean::class)
            )]),
    )
    fun findForChange(@PathParam("desiredNickname") desiredNickname:String):Boolean{
        val reference = jwt.name
        return queryNicknamesOut.doNicknameExist(reference, desiredNickname)
    }

    @GET
    @Path("{desiredNickname}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @PermitAll
    @APIResponses(
        APIResponse(responseCode = "200", description = "Boolean representing the nickname existence in database",
            content = [Content(mediaType = "text/plain",
                schema = Schema(implementation = Boolean::class)
            )]),
    )
    fun findForAdd(@PathParam("desiredNickname") desiredNickname:String):Boolean{
        val reference = jwt.name
        return queryNicknamesOut.doNicknameExist(reference, desiredNickname)
    }
}