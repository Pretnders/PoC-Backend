package pretnders.bootstrap.persistence

import io.quarkus.hibernate.orm.runtime.tenant.TenantResolver
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.config.inject.ConfigProperty

@ApplicationScoped
class Resolver : TenantResolver {
    @field:ConfigProperty(name = "azure.tenant-id")
    private lateinit var tenantId: String

    override fun getDefaultTenantId(): String {
        return tenantId
    }

    override fun resolveTenantId(): String {
        return tenantId
    }
}