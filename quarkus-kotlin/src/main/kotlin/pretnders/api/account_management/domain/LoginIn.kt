package pretnders.api.account_management.domain

import pretnders.api.admins.domain.AdminLoggedIn
import pretnders.api.pretnders.domain.models.PretnderLoggedIn

interface LoginIn {
    fun loginAdmin(identifier: String, password: String): AdminLoggedIn
    fun loginPretnder(identifier: String, password: String): PretnderLoggedIn

}