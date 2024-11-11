package com.pretnders.domain.ports.`in`

import com.pretnders.domain.models.admins.CreateAdminCommand
import com.pretnders.domain.models.pretnders.UserBasicInformations

interface CreateAdminIn {
    fun createAdmin(createAdminCommand: CreateAdminCommand): UserBasicInformations

}