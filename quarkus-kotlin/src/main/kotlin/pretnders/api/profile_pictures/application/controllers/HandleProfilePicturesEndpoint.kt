package pretnders.api.profile_pictures.application.controllers

import jakarta.annotation.security.RolesAllowed
import jakarta.enterprise.context.RequestScoped
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.jwt.Claims
import org.eclipse.microprofile.jwt.JsonWebToken
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement
import org.jboss.resteasy.reactive.PartType
import org.jboss.resteasy.reactive.ResponseStatus
import org.jboss.resteasy.reactive.RestForm
import org.jboss.resteasy.reactive.RestResponse.StatusCode.*
import org.jboss.resteasy.reactive.multipart.FileUpload
import pretnders.api.azure.domain.ports.out.StorageClientOut
import pretnders.api.profile_pictures.application.dto.requests.DeletePictureRequest
import pretnders.api.profile_pictures.application.dto.requests.SwapPicturesRequest
import pretnders.api.profile_pictures.application.dto.responses.AddProfilePictureResponse
import pretnders.api.profile_pictures.domain.ports.`in`.RemoveProfilePictureCommand
import pretnders.api.profile_pictures.domain.ports.`in`.HandleProfilePicturesIn
import pretnders.api.profile_pictures.domain.ports.`in`.SwapProfilePictureCommand
import pretnders.api.profile_pictures.domain.ports.`in`.ChangeProfilePictureCommand

@Path("/profile-pictures")
@RequestScoped
class HandleProfilePicturesEndpoint (
    private val jwt: JsonWebToken,
    private val storageClientOut: StorageClientOut,
    private val handleProfilePicturesIn: HandleProfilePicturesIn
){

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
    fun addPretnderProfilePicture(
        @PartType(MediaType.MULTIPART_FORM_DATA ) @RestForm("image")
                                  image:FileUpload
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
    @Operation(summary = "Swap pictures", description = "Swap 2 pretnder profile picture order")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO-CONTENT", content = [Content(mediaType = "text/plain",
            schema = Schema(implementation = String::class)
        )]),
    )
    fun swapPicturesOrder( swapPicturesRequest: SwapPicturesRequest) {
        val command = SwapProfilePictureCommand(
            swapPicturesRequest.swapperReference, swapPicturesRequest.swapperOrder,
            swapPicturesRequest.swappedReference, swapPicturesRequest.swappedOrder
        )
        handleProfilePicturesIn.swapPicturesOrder(command)
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
        val command = RemoveProfilePictureCommand(
            deletePictureRequest.reference,
            deletePictureRequest.blobName,
            phoneNumber
        )
        handleProfilePicturesIn.removeProfilePicture(command)
    }

    @PUT
    @Path("/pretnders/change-picture/{pictureReference}/{blobName}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    @ResponseStatus(OK)
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
    fun replacePicture(@PathParam("pictureReference") pictureReference:String, @PathParam("blobName") blobName:String,
                       @PartType(MediaType.MULTIPART_FORM_DATA ) @RestForm("image") image: FileUpload):
    String {
        val phoneNumber = jwt.claim<String>(Claims.phone_number.name).get()
        val command = ChangeProfilePictureCommand(
            pictureReference,
            blobName,
            image,
            phoneNumber
        )
        return handleProfilePicturesIn.changeProfilePicture(command)
    }
}