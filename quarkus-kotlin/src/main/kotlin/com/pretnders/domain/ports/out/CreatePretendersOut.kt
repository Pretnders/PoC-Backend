package com.pretnders.domain.ports.out

import com.pretnders.domain.models.pretnders.CreatePretenderCommand

interface CreatePretendersOut {
    fun addPretender(pretender: CreatePretenderCommand)
}