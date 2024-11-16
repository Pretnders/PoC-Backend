package com.pretnders.azure.domain.ports.out

interface SecretsClientOut {
    fun updateAdminCode(adminCode: String)
    fun getCurrentAdminCreationCode():String

}