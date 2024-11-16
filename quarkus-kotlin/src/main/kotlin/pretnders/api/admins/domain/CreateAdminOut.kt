package pretnders.api.admins.domain

interface CreateAdminOut {
    fun addAdmin(createAdminCommand: CreateAdminCommand)
}