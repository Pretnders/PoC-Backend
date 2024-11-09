package com.pretnders.domain.ports.out

import com.pretnders.domain.models.commands.users.CreateAdminCommand

interface CreateAdminOut {
    fun addAdmin(createAdminCommand: CreateAdminCommand)
}