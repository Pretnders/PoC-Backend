package pretnders.api.pretnders.domain.ports.out

import pretnders.api.pretnders.domain.models.CreatePretenderCommand

interface AddPretndersOut {
    fun addPretender(pretender: CreatePretenderCommand)
}