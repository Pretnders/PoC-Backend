package com.pretnders.pretnders.domain.ports.out

import com.pretnders.pretnders.domain.models.Pretnder

interface FindPretndersOut {
    fun findByIdentifier(identifier: String) : Pretnder
    fun findIDByReference(reference: String) : Long
}