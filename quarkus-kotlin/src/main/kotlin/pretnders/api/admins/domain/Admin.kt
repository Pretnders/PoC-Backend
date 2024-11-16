package pretnders.api.admins.domain

data class Admin(
    val id:Long?=null,
    val nickname: String,
    val password: String,
    val mail: String,
    val reference: String,
    val phoneNumber: String,
    val profilePictureUrl: String? = null
)
