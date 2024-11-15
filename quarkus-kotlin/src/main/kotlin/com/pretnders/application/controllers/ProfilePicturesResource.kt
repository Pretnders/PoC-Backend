package com.pretnders.application.controllers

import com.pretnders.application.dto.requests.DeletePictureRequest
import com.pretnders.application.dto.requests.SwapPicturesRequest
import com.pretnders.application.dto.requests.UpdatePictureRequest
import com.pretnders.application.dto.responses.AddProfilePictureResponse
import com.pretnders.application.utils.CookieUtils
import com.pretnders.domain.ports.`in`.AzureStorageIn
import com.pretnders.domain.ports.`in`.CsrfTokenGeneratorIn
import com.pretnders.domain.ports.`in`.ProfilePicturesIn
import jakarta.annotation.security.RolesAllowed
import jakarta.enterprise.context.RequestScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.POST
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.jwt.Claims
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
import org.jboss.resteasy.reactive.RestResponse.StatusCode.*
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
    private lateinit var profilePicturesIn: ProfilePicturesIn

    @Inject
    @field:Default
    private lateinit var csrfTokenGeneratorIn: CsrfTokenGeneratorIn

    @field:ConfigProperty(name="quarkus.rest.csrf.cookie.name")
    private lateinit var csrfCookieName: String

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @ResponseStatus(ACCEPTED)
    @RolesAllowed("ADMIN")
    @SecurityRequirement(name = "bearer")
    @Operation(summary = "Update admin profile picture", description = "Modifies the admin profile picture, update " +
            "the" +
            " " +
            "link in db, returns the new url")
    @APIResponses(
        APIResponse(responseCode = "201", description = "OK", content = [Content(mediaType = "text/plain",
            schema = Schema(implementation = String::class)
        )]),
    )
    fun updateAdminProfilePicture(@Schema(type = SchemaType.STRING, format = "binary") @RestForm("image")  image: FileUpload
    ): Response {
        val userMail = getMailClaim()
        val phoneNumber = jwt.claim<String>(Claims.phone_number.name).get()
        val profilePicUrl = azureStorageIn.updateAdminProfilePicture(
            phoneNumber,
            image)
        val csrfToken = csrfTokenGeneratorIn.generateToken(userMail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        return Response.ok(profilePicUrl).header("x-csrf-token", csrfToken).cookie(csrfCookie).build()
    }

    @POST
    @Path("/pretnders")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @ResponseStatus(CREATED)
    @RolesAllowed("PRETNDER")
    @SecurityRequirement(name = "bearer")
    @Operation(summary = "Add pretnder profile picture", description = "Add pretnder profile picture, update " +
            "the" +
            " " +
            "link in db, returns the new url")
    @APIResponses(
        APIResponse(responseCode = "200", description = "OK", content = [Content(mediaType = "text/plain",
            schema = Schema(implementation = String::class)
        )]),
    )
    fun addPretnderProfilePicture( @Schema(type = SchemaType.STRING,
        format = "binary") @RestForm
        ("image")  image:
    FileUpload
    ): Response {
        val reference = jwt.name
        val userMail = getMailClaim()
        val phoneNumber = jwt.claim<String>(Claims.phone_number.name).get()
        val response = profilePicturesIn.addProfilePicture(reference,
            phoneNumber,
            image)
        val addProfilePicResponse = AddProfilePictureResponse(response.reference, response.url)
        val csrfToken = csrfTokenGeneratorIn.generateToken(userMail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        return Response.ok(addProfilePicResponse).header("x-csrf-token", csrfToken).status(Response.Status.CREATED).cookie(csrfCookie).build()
    }

    @PUT
    @Path("/pretnders/change-picture")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @SecurityRequirement(name = "bearer")
    @Operation(summary = "Add pretnder profile picture", description = "Add pretnder profile picture, update " +
            "the" +
            " " +
            "link in db, returns the new url")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO-CONTENT", content = [Content(mediaType = "text/plain",
            schema = Schema(implementation = String::class)
        )]),
    )
    fun swapPicturesOrder( swapPicturesRequest: SwapPicturesRequest): Response {
        val userMail = getMailClaim()
        val csrfToken = csrfTokenGeneratorIn.generateToken(userMail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        profilePicturesIn.swapPicturesOrder(swapPicturesRequest.swapperReference, swapPicturesRequest.swapperOrder,
            swapPicturesRequest.swappedReference, swapPicturesRequest.swappedOrder,)
        return Response.status(Response.Status.NO_CONTENT).header("x-csrf-token", csrfToken).cookie(csrfCookie).build()
    }


    @DELETE
    @Path("/pretnders")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @SecurityRequirement(name = "bearer")
    @Operation(summary = "Add pretnder profile picture", description = "Add pretnder profile picture, update " +
            "the" +
            " " +
            "link in db, returns the new url")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO-CONTENT", content = [Content(mediaType = "text/plain",
            schema = Schema(implementation = String::class)
        )]),
    )
    fun deletePicture( deletePictureRequest: DeletePictureRequest): Response {
        val userMail = getMailClaim()
        val phoneNumber = jwt.claim<String>(Claims.phone_number.name).get()
        val csrfToken = csrfTokenGeneratorIn.generateToken(userMail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        profilePicturesIn.deleteProfilePicture(deletePictureRequest.reference, deletePictureRequest.blobName, phoneNumber)
        return Response.status(Response.Status.NO_CONTENT).header("x-csrf-token", csrfToken).cookie(csrfCookie).build()
    }

    @PUT
    @Path("/pretnders/change-picture/{pictureReference}/{blobName}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @SecurityRequirement(name = "bearer")
    @Operation(summary = "Add pretnder profile picture", description = "Add pretnder profile picture, update " +
            "the" +
            " " +
            "link in db, returns the new url")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO-CONTENT", content = [Content(mediaType = "text/plain",
            schema = Schema(implementation = String::class)
        )]),
    )
    fun updatePicture(@PathParam("pictureReference") pictureReference:String, @PathParam("blobName") blobName:String,
                      @RestForm("image") image: FileUpload):
    Response {
        val userMail = getMailClaim()
        val phoneNumber = jwt.claim<String>(Claims.phone_number.name).get()
        val csrfToken = csrfTokenGeneratorIn.generateToken(userMail)
        val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
        val newUrl = profilePicturesIn.updateProfilePicture(pictureReference, blobName, image, phoneNumber)
        return Response.ok(newUrl).header("x-csrf-token", csrfToken).cookie(csrfCookie).build()
    }

    private fun getMailClaim() = jwt.claim<String>(Claims.email.name).get()
}