package com.pretnders.domain.ports.out

import com.pretnders.domain.models.pretnders.Pretnder

interface FindPretendersOut {
    fun findByIdentifier(identifier: String) : Pretnder
    fun findIDByReference(reference: String) : Long
}