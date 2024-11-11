package com.pretnders.domain.models.admins

data class Admins(
    val id:Long?=null,
    val nickname: String,
    val password: String,
    val mail: String,
    val reference: String,
    val phoneNumber: String,
    val profilePictureUrl: String? = null
)
