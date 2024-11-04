package com.templates.domain.services

import com.templates.domain.ports.`in`.AdminCodeIn
import com.templates.domain.ports.out.SecretsClientOut
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