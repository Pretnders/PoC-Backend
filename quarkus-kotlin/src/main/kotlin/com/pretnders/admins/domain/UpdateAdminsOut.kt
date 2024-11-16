package com.pretnders.admins.domain



interface UpdateAdminsOut {
    fun updateNickname(mail: String, newNickname: String)
    fun updateProfilePicture(phoneNumber: String, newProfilePicture: String)
}