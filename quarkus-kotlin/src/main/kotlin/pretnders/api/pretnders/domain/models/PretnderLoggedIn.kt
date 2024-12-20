package pretnders.api.pretnders.domain.models

import pretnders.api.profile_pictures.domain.models.ProfilePicture

data class PretnderLoggedIn(
    val nickname:String,
    val firstName: String,
    val lastName: String,
    val mail: String,
    val phoneNumber: String,
    val reference: String,
    val jwToken:String,
    val accountVerifiedStatus:Boolean,
    val pretnderDetails: PretnderDetails,
    var profilePictures: List<ProfilePicture>? = ArrayList(),
    var traitPairs: List<PretnderTraitPair>
){
    override fun toString(): String {
        return "PretnderLoggedIn(nickname='$nickname', firstName='$firstName', lastName='$lastName', mail='$mail', phoneNumber='$phoneNumber', reference='$reference', jwToken='$jwToken', accountVerifiedStatus=$accountVerifiedStatus, pretnderDetails=$pretnderDetails, profilePictures=$profilePictures, traitPairs=$traitPairs)"
    }
}
