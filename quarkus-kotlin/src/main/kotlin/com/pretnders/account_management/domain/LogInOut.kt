package com.pretnders.account_management.domain

import com.pretnders.admins.domain.Admin
import com.pretnders.pretnders.domain.models.Pretnder

interface LogInOut {
    fun findAdminByIdentifier(identifier:String) : Admin
    fun findPretnderByIdentifier(identifier:String) : Pretnder

}