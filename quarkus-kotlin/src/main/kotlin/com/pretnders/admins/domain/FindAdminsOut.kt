package com.pretnders.admins.domain

interface FindAdminsOut {
    fun findByIdentifier(identifier: String) : Admin
    fun findIDByReference(reference: String) : Long
}