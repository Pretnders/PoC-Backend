package com.pretnders.admins.application

import com.pretnders.shared.utils.generators.ports.`in`.AdminCodeIn
import io.quarkus.logging.Log
import jakarta.annotation.security.PermitAll
import jakarta.enterprise.context.RequestScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import org.eclipse.microprofile.jwt.JsonWebToken
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.jboss.resteasy.reactive.ResponseStatus
import org.jboss.resteasy.reactive.RestResponse.StatusCode.OK


@Path("/admin-code")
@RequestScoped
class AdminCodeResource {

    @Inject
    @field:Default
    private lateinit var adminCodeIn: AdminCodeIn

    @Inject
    @field:Default
    private lateinit var jwt: JsonWebToken


    @GET
    @ResponseStatus(OK)
    @APIResponses(
        APIResponse(responseCode = "200", description = "OK", content = [Content(mediaType = "text/plain",
            schema = Schema(implementation = String::class))]),
    )
    @PermitAll
    fun getAdminCode(): String {
        Log.info("Retrieving new admin code")
        return adminCodeIn.getCurrentCode()

    }
}