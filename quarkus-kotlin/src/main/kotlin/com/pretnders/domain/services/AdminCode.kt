package com.pretnders.domain.services

import com.pretnders.domain.ports.`in`.AdminCodeIn
import com.pretnders.domain.ports.out.SecretsClientOut
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject

@ApplicationScoped
class AdminCode :AdminCodeIn{
    @Inject
    @field:Default
    private lateinit var secretsClientOut: SecretsClientOut
    override fun getCurrentCode(): String {
        return secretsClientOut.getCurrentAdminCreationCode()
    }
}