package com.pretnders.shared.security

import com.pretnders.shared.utils.generators.AdminCodeGenerator.generateAdminCode
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class CsrfTokenGenerator: CsrfTokenGeneratorIn {

    override fun generateToken(): String {
        return generateAdminCode()
    }
}