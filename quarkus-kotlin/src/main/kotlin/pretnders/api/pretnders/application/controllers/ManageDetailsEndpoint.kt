package pretnders.api.pretnders.application.controllers

import jakarta.annotation.security.RolesAllowed
import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.jwt.JsonWebToken
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.jboss.resteasy.reactive.ResponseStatus
import org.jboss.resteasy.reactive.RestResponse.StatusCode.NO_CONTENT
import pretnders.api.pretnders.application.dto.requests.ChangeBiographyRequest
import pretnders.api.pretnders.application.dto.requests.ChangeNicknameRequest
import pretnders.api.pretnders.domain.models.details_enums.*
import pretnders.api.pretnders.domain.ports.`in`.ChangeDetailsIn
import pretnders.api.pretnders.domain.ports.out.FindNicknameOut

@Path("/pretnder-profile")
@RequestScoped
class ManageDetailsEndpoint {

    @Inject
    private lateinit var jwt:JsonWebToken

    @Inject
    private lateinit var changeDetailsIn: ChangeDetailsIn

    @Inject
    private lateinit var findNicknameOut: FindNicknameOut


    @PUT
    @Path("/nickname")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun changeNickname(changeNicknameRequest: ChangeNicknameRequest) {
        val reference = jwt.name
        changeDetailsIn.changeNickname(reference, changeNicknameRequest.newNickname)
    }

    @PUT
    @Path("/height/{detailsReference}/{newHeight}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun update(@PathParam("detailsReference") detailsReference:String, @PathParam
        ("newHeight") newHeight:String) {
        changeDetailsIn.changeHeight(detailsReference, newHeight)
    }

    @PUT
    @Path("/body-type/{detailsReference}/{newBodyType}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun update(@PathParam("detailsReference") detailsReference:String, @PathParam
        ("newBodyType") newBodyType: BodyTypes
    ) {
        changeDetailsIn.changeBodyType(detailsReference, newBodyType)
    }

    @PUT
    @Path("/diet/{detailsReference}/{newDiet}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun update(@PathParam("detailsReference") detailsReference:String, @PathParam
        ("newDiet") newDiet: Diets
    ) {
        changeDetailsIn.changeDiet(detailsReference, newDiet)
    }

    @PUT
    @Path("/beliefs/{detailsReference}/{newBelief}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun update(@PathParam("detailsReference") detailsReference:String, @PathParam
        ("newBelief") newBelief: Beliefs
    ) {
        changeDetailsIn.changeBeliefs(detailsReference, newBelief)
    }

    @PUT
    @Path("/smokes/{detailsReference}/{newOccurrence}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun updateSmokes(@PathParam("detailsReference") detailsReference:String, @PathParam
        ("newOccurrence") newOccurrence: Occurrences
    ) {
        changeDetailsIn.changeSmokes(detailsReference, newOccurrence)
    }

    @PUT
    @Path("/drinks/{detailsReference}/{newOccurrence}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun updateDrinks(@PathParam("detailsReference") detailsReference:String, @PathParam
        ("newOccurrence") newOccurrence: Occurrences
    ) {
        changeDetailsIn.changeDrinks(detailsReference, newOccurrence)
    }

    @PUT
    @Path("/social-status/{detailsReference}/{newSocialStatus}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun update(@PathParam("detailsReference") detailsReference:String, @PathParam
        ("newSocialStatus") newSocialStatus: SocialStatus
    ) {
        changeDetailsIn.changeSocialStatus(detailsReference, newSocialStatus)
    }

    @PUT
    @Path("/biography")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun update(changeBiographyRequest: ChangeBiographyRequest) {
        changeDetailsIn.changeBiography(changeBiographyRequest.reference, changeBiographyRequest.biography)
    }

    @PUT
    @Path("/city/{detailsReference}/{newCity}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun updateCity(@PathParam("detailsReference") detailsReference:String, @PathParam("newCity") newCity:String) {
        changeDetailsIn.changeCity(detailsReference, newCity)
    }

    @PUT
    @Path("/country/{detailsReference}/{newCountry}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun updateCountry(@PathParam("detailsReference") detailsReference:String, @PathParam("newCountry") newCountry:String){
        changeDetailsIn.changeCountry(detailsReference, newCountry)
    }

    @PUT
    @Path("/orientation/{detailsReference}/{newOrientation}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun update(@PathParam("detailsReference") detailsReference:String, @PathParam
        ("newOrientation") newOrientation: SexualOrientations
    ) {
        changeDetailsIn.changeOrientation(detailsReference, newOrientation)
    }

    @PUT
    @Path("/gender/{detailsReference}/{newGender}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun update(@PathParam("detailsReference") detailsReference:String, @PathParam
        ("newGender") newGender: Genders
    ) {
        changeDetailsIn.changeGender(detailsReference, newGender)
    }

    @GET
    @Path("/nickname/{desiredNickname}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    @APIResponses(
        APIResponse(responseCode = "200", description = "Boolean representing the nickname existence in database",
            content = [Content(mediaType = "text/plain",
            schema = Schema(implementation = Boolean::class)
        )]),
    )
    fun find(@PathParam("desiredNickname") desiredNickname:String):Boolean{
        val reference = jwt.name
        return findNicknameOut.doNicknameExist(reference, desiredNickname)
    }
}