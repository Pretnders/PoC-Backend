package pretnders.api.admins.domain

interface FindAdminsOut {
    fun findByIdentifier(identifier: String) : Admin
    fun findIDByReference(reference: String) : Long
}