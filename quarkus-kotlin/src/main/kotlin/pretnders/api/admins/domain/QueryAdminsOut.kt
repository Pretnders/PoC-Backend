package pretnders.api.admins.domain

interface QueryAdminsOut {
    fun findByIdentifier(identifier: String) : Admin
    fun findIDByReference(reference: String) : Long
}