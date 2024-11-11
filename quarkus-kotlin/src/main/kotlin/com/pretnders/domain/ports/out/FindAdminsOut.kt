package com.pretnders.domain.ports.out

import com.pretnders.domain.models.admins.Admins

interface FindAdminsOut {
    fun findByIdentifier(identifier: String) : Admins
    fun findIDByReference(reference: String) : Long
}