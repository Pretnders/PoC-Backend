package com.pretnders.domain.ports.`in`

import com.pretnders.domain.models.commands.users.CreatePretenderCommand
import com.pretnders.domain.models.users.UserBasicInformations

interface CreatePretendersIn {
    fun createPretender(user:CreatePretenderCommand): UserBasicInformations

}