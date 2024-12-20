package pretnders.api.admins.application

import kotlinx.serialization.Serializable

@Serializable
data class AdminLoginResponse(
    val nickname:String,
    val mail: String,
    val phoneNumber: String,
    val reference: String,
    val profilePictureUrl: String? = null
){
    override fun toString(): String {
        return "AdminLoginResponse(nickname='$nickname', mail='$mail', phoneNumber='$phoneNumber', reference='$reference', profilePictureUrl=$profilePictureUrl)"
    }
}
