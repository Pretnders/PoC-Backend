package com.pretnders.domain.ports.out

import com.pretnders.domain.models.users.Pretnder

interface FindPretendersOut {
    fun findByIdentifier(identifier: String) : Pretnder

}