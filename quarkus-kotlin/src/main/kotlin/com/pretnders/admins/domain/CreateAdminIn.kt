package com.pretnders.admins.domain

import com.pretnders.shared.utils.shared_models.UserBasicInformations

interface CreateAdminIn {
    fun createAdmin(createAdminCommand: CreateAdminCommand): UserBasicInformations

}