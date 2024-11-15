package com.pretnders.domain.services

import com.pretnders.domain.ports.`in`.CsrfTokenGeneratorIn
import com.pretnders.domain.utils.AdminCodeGenerator.generateAdminCode
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class CsrfTokenGenerator:CsrfTokenGeneratorIn {

    override fun generateToken(identifier:String): String {
        return generateAdminCode()
    }
}