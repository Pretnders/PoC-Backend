package com.pretnders.domain.ports.out

import com.pretnders.domain.models.users.Pretnder

interface FindAdminsOut {
    fun findByIdentifier(identifier: String) : Pretnder
}