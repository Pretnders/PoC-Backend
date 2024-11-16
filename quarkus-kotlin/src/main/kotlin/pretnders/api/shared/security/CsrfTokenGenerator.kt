package pretnders.api.shared.security

import pretnders.api.shared.utils.generators.AdminCodeGenerator.generateAdminCode
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class CsrfTokenGenerator: CsrfTokenGeneratorIn {

    override fun generateToken(): String {
        return generateAdminCode()
    }
}