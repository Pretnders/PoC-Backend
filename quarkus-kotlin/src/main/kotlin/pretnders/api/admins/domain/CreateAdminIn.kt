package pretnders.api.admins.domain

import pretnders.api.shared.utils.shared_models.UserBasicInformations

interface CreateAdminIn {
    fun createAdmin(createAdminCommand: CreateAdminCommand): UserBasicInformations

}