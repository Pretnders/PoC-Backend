package com.pretnders.domain.ports.out

import com.pretnders.domain.models.users.User

interface FindClientsOut {
    fun findByIdentifier(identifier: String) : User
    fun findByPasswordVerificationCode(passwordVerificationCode: String) : User

}