package com.pretnders.domain.ports.out

import com.pretnders.domain.models.commands.users.CreatePretenderCommand

interface CreatePretendersOut {
    fun addPretender(pretender: CreatePretenderCommand)
}