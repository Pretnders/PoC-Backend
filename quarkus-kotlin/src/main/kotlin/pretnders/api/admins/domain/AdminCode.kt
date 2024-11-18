package pretnders.api.admins.domain

import jakarta.enterprise.context.ApplicationScoped
import pretnders.api.azure.domain.ports.out.SecretsClientOut
import pretnders.api.shared.utils.generators.ports.`in`.AdminCodeIn

@ApplicationScoped
class AdminCode (
    private val secretsClientOut: SecretsClientOut

) : AdminCodeIn {
    override fun getCurrentCode(): String {
        return secretsClientOut.getCurrentAdminCreationCode()
    }
}