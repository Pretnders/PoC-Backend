package com.pretnders.domain.ports.out

import com.pretnders.domain.models.users.User

interface FindAdminsOut {
    fun findByIdentifier(identifier: String) : User
}