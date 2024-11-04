package com.pretnders.application.controllers

import com.pretnders.application.dto.responses.UpdateProfilePictureResponse
import com.pretnders.domain.errors.ApplicationException
import com.pretnders.domain.errors.ApplicationExceptionsEnum
import com.pretnders.domain.ports.`in`.AzureStorageIn
import com.pretnders.domain.ports.`in`.CsrfTokenGeneratorIn
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
import org.eclipse.microprofile.jwt.JsonWebToken
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement
import org.jboss.resteasy.reactive.ResponseStatus
import org.jboss.resteasy.reactive.RestForm
import org.jboss.resteasy.reactive.RestResponse.StatusCode.ACCEPTED
import org.jboss.resteasy.reactive.multipart.FileUpload

@Path("/profile-pictures")
@RequestScoped
class ProfilePicturesResource {
    @Inject
    @field: Default
    private lateinit var cookieUtils: CookieUtils
    @Inject
    @field:Default
    private lateinit var jwt: JsonWebToken

    @Inject
    @field:Default
    private lateinit var azureStorageIn: AzureStorageIn

    @Inject
    @field:Default
    private lateinit var csrfTokenGeneratorIn: CsrfTokenGeneratorIn

    @field:ConfigProperty(name="quarkus.rest-csrf.cookie-name")
    private lateinit var csrfCookieName: String

    @PUT
    @Path("/{phoneNumber}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @ResponseStatus(ACCEPTED)
    @RolesAllowed("PRETENDER","ADMIN")
    @SecurityRequirement(name = "bearer")
    @Operation(summary = "Update user profile picture", description = "Modifies the user profile picture, update the " +
            "link in db, returns the new url")
    @APIResponses(
        APIResponse(responseCode = "200", description = "OK", content = [Content(mediaType = "text/plain",
            schema = Schema(implementation = String::class)
        )]),
    )
    fun updateClientProfilePicture(phoneNumber:String, @Schema(type = SchemaType.STRING,
    format = "binary") @RestForm
        ("image")  image:
                                    FileUpload
    ): Response {
        val userMail = jwt.name
        val userType = jwt.groups.stream().findFirst().orElseThrow { ApplicationException(ApplicationExceptionsEnum.ERROR)}
        val profilePicUrl = UpdateProfilePictureResponse(azureStorageIn.updateProfilePicture(userMail,userType,
            phoneNumber,
            image))
        val csrfToken = csrfTokenGeneratorIn.generateToken(userMail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        return Response.ok(profilePicUrl).cookie(csrfCookie).build()
    }
}