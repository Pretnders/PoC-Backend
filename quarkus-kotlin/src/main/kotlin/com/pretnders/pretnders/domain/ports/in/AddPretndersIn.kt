package com.pretnders.pretnders.domain.ports.`in`

import com.pretnders.pretnders.domain.models.CreatePretenderCommand
import com.pretnders.shared.utils.shared_models.UserBasicInformations

interface AddPretndersIn {
    fun createPretender(user: CreatePretenderCommand): UserBasicInformations

}