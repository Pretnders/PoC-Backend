package com.templates.domain.services

import com.templates.domain.ports.`in`.CsrfTokenGeneratorIn
import com.templates.domain.utils.AdminCodeGenerator.generateAdminCode
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject

@ApplicationScoped
class CsrfTokenGenerator:CsrfTokenGeneratorIn {
    @Inject
    @field:Default
    private lateinit var csrfTokenCache: CsrfTokenCache

    override fun generateToken(identifier:String): String {
        val token = generateAdminCode()
        csrfTokenCache.addItem(identifier, token)
        return token
    }
}