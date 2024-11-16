package com.pretnders.account_management.application

import kotlinx.serialization.Serializable

@Serializable
data class PasswordChangeRequest(val currentPassword:String, val password:String, val passwordConfirmation:String)
