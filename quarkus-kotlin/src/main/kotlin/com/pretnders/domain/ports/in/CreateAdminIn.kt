package com.pretnders.domain.ports.`in`

import com.pretnders.domain.models.commands.users.CreateAdminCommand
import com.pretnders.domain.models.users.UserBasicInformations

interface CreateAdminIn {
    fun createAdmin(createAdminCommand: CreateAdminCommand): UserBasicInformations

}