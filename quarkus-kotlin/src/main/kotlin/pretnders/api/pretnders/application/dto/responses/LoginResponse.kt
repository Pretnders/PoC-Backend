package pretnders.api.pretnders.application.dto.responses

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
    val pretnderDetails: PretnderDetailsResponse,
    var profilePictures: List<PretnderProfilePictureResponse>? = ArrayList(),
    var traitPairs: List<PretnderTraitScoreResponse>
){
    override fun toString(): String {
        return "PretnderLoginResponse(nickname='$nickname', firstName='$firstName', lastName='$lastName', phoneNumber='$phoneNumber', mail='$mail', accountVerifiedStatus=$accountVerifiedStatus, pretnderDetails=$pretnderDetails, profilePictures=$profilePictures, traitPairs=$traitPairs)"
    }
}
