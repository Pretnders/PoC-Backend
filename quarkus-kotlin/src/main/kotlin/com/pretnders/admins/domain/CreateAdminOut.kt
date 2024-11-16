package com.pretnders.admins.domain

interface CreateAdminOut {
    fun addAdmin(createAdminCommand: CreateAdminCommand)
}