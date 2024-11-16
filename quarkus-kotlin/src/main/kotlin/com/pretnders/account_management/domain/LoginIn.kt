package com.pretnders.account_management.domain

import com.pretnders.admins.domain.AdminLoggedIn
import com.pretnders.pretnders.domain.models.PretnderLoggedIn

interface LoginIn {
    fun loginAdmin(identifier: String, password: String): AdminLoggedIn
    fun loginPretnder(identifier: String, password: String): PretnderLoggedIn

}