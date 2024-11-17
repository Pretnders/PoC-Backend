package pretnders.api.admins.domain

class AdminLoggedIn(
    val nickname:String,
    val mail: String,
    val phoneNumber: String,
    val reference: String,
    val jwToken:String,
    val profilePictureUrl:String? = null,
){
    override fun toString(): String {
        return "AdminLoggedIn(nickname='$nickname', mail='$mail', phoneNumber='$phoneNumber', reference='$reference', jwToken='$jwToken', profilePictureUrl='$profilePictureUrl'"
    }
}
