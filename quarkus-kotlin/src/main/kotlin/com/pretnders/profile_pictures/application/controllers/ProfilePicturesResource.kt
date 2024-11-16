package com.pretnders.profile_pictures.application.controllers

import com.pretnders.profile_pictures.application.dto.requests.DeletePictureRequest
import com.pretnders.profile_pictures.application.dto.requests.SwapPicturesRequest
import com.pretnders.profile_pictures.application.dto.responses.AddProfilePictureResponse
import com.pretnders.shared.security.CookieUtils
import com.pretnders.azure.domain.ports.out.StorageClientOut
import com.pretnders.shared.security.CsrfTokenGeneratorIn
import com.pretnders.profile_pictures.domain.ports.`in`.HandleProfilePicturesIn
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
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
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
    private lateinit var storageClientOut: StorageClientOut

    @Inject
    @field:Default
    private lateinit var handleProfilePicturesIn: HandleProfilePicturesIn

    @Inject
    @field:Default
    private lateinit var csrfTokenGeneratorIn: CsrfTokenGeneratorIn

    @field:ConfigProperty(name="quarkus.rest.csrf.cookie.name")
    private lateinit var csrfCookieName: String

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
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
    ): String {
        val phoneNumber = jwt.claim<String>(Claims.phone_number.name).get()
        val profilePicUrl = storageClientOut.updateAdminProfilePicture(
            phoneNumber,
            image)
        return profilePicUrl
    }

    @POST
    @Path("/pretnders")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
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
    ): AddProfilePictureResponse {
        val reference = jwt.name
        val phoneNumber = jwt.claim<String>(Claims.phone_number.name).get()
        val response = handleProfilePicturesIn.addProfilePicture(reference,
            phoneNumber,
            image)
        return AddProfilePictureResponse(response.reference, response.url)
    }

    @PUT
    @Path("/pretnders")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
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
    fun swapPicturesOrder( swapPicturesRequest: SwapPicturesRequest) {
        handleProfilePicturesIn.swapPicturesOrder(swapPicturesRequest.swapperReference, swapPicturesRequest.swapperOrder,
            swapPicturesRequest.swappedReference, swapPicturesRequest.swappedOrder,)
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
    fun deletePicture( deletePictureRequest: DeletePictureRequest) {
        val phoneNumber = jwt.claim<String>(Claims.phone_number.name).get()
        handleProfilePicturesIn.deleteProfilePicture(deletePictureRequest.reference, deletePictureRequest.blobName, phoneNumber)
    }

    @PUT
    @Path("/pretnders/change-picture/{pictureReference}/{blobName}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
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
    String {
        val phoneNumber = jwt.claim<String>(Claims.phone_number.name).get()
        return handleProfilePicturesIn.updateProfilePicture(pictureReference, blobName, image, phoneNumber)
    }
}