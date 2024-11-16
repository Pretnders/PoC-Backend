package com.pretnders.admins.domain

class CreateAdminCommand(
    val nickname: String,
    val adminCode: String,
    val mail: String,
    var password: String,
    val phoneNumber: String,
    var reference: String? = null,
) {

    override fun toString(): String {
        return "CreateAdminCommand(nickname='$nickname', adminCode='$adminCode', " +
                "mail='$mail', password='$password', phoneNumber='$phoneNumber', reference=$reference)"
    }
}
