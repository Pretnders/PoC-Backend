package com.pretnders.domain.ports.out

import com.pretnders.domain.models.users.User

interface FindPretendersOut {
    fun findByIdentifier(identifier: String) : User

}