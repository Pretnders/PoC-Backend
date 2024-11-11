package com.pretnders.domain.ports.out

import com.pretnders.domain.models.admins.CreateAdminCommand

interface CreateAdminOut {
    fun addAdmin(createAdminCommand: CreateAdminCommand)
}