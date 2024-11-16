package pretnders.api.pretnders.domain.ports.`in`

import pretnders.api.pretnders.domain.models.CreatePretenderCommand
import pretnders.api.shared.utils.shared_models.UserBasicInformations

interface AddPretndersIn {
    fun createPretender(user: CreatePretenderCommand): UserBasicInformations

}