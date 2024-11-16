package pretnders.api.account_management.domain

import pretnders.api.admins.domain.Admin
import pretnders.api.pretnders.domain.models.Pretnder

interface LogInOut {
    fun findAdminByIdentifier(identifier:String) : Admin
    fun findPretnderByIdentifier(identifier:String) : Pretnder

}