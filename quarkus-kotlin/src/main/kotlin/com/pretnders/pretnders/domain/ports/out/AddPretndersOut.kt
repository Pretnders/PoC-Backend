package com.pretnders.pretnders.domain.ports.out

import com.pretnders.pretnders.domain.models.CreatePretenderCommand

interface AddPretndersOut {
    fun addPretender(pretender: CreatePretenderCommand)
}