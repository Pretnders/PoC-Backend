package com.pretnders.domain.ports.`in`

import com.pretnders.domain.models.admins.AdminLoggedIn
import com.pretnders.domain.models.pretnders.PretnderLoggedIn

interface LoginIn {
    fun loginAdmin(identifier: String, password: String): AdminLoggedIn
    fun loginPretnder(identifier: String, password: String): PretnderLoggedIn

}