package com.pretnders.domain.ports.out

import com.pretnders.domain.models.users.Pretnder

interface FindClientsOut {
    fun findByIdentifier(identifier: String) : Pretnder
    fun findByPasswordVerificationCode(passwordVerificationCode: String) : Pretnder

}