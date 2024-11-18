package pretnders.api.pretnders.application.dto.responses

import pretnders.api.profile_pictures.application.dto.responses.ProfilePictureResponse
import kotlinx.serialization.Serializable
import org.eclipse.microprofile.openapi.annotations.media.Schema

@Serializable
@Schema(description = "Login response")
data class LoginResponse(
    val nickname:String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val mail:String,
    val accountVerifiedStatus: Boolean,
    val pretnderDetails: FindDetailsResponse,
    var profilePictures: List<ProfilePictureResponse>? = ArrayList(),
    var traitPairs: List<FindTraitScoreResponse>
){
    override fun toString(): String {
        return "PretnderLoginResponse(nickname='$nickname', firstName='$firstName', lastName='$lastName', phoneNumber='$phoneNumber', mail='$mail', accountVerifiedStatus=$accountVerifiedStatus, pretnderDetails=$pretnderDetails, profilePictures=$profilePictures, traitPairs=$traitPairs)"
    }
}
