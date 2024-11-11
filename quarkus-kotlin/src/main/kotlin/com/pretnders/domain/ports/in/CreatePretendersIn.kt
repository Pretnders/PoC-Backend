package com.pretnders.domain.ports.`in`

import com.pretnders.domain.models.pretnders.CreatePretenderCommand
import com.pretnders.domain.models.pretnders.UserBasicInformations

interface CreatePretendersIn {
    fun createPretender(user: CreatePretenderCommand): UserBasicInformations

}