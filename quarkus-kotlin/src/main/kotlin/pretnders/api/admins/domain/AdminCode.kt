package pretnders.api.admins.domain

import pretnders.api.shared.utils.generators.ports.`in`.AdminCodeIn
import pretnders.api.azure.domain.ports.out.SecretsClientOut
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject

@ApplicationScoped
class AdminCode : AdminCodeIn {
    @Inject
    @field:Default
    private lateinit var secretsClientOut: SecretsClientOut
    override fun getCurrentCode(): String {
        return secretsClientOut.getCurrentAdminCreationCode()
    }
}