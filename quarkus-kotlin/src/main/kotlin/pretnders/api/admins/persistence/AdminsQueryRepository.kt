package pretnders.api.admins.persistence

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import io.quarkus.panache.common.Parameters
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class AdminsQueryRepository : PanacheRepository<AdminsEntity> {

    fun findByIdentifier(identifier: String): AdminsEntity? {
        return find("SELECT u from AdminsEntity u WHERE (u.mail =:mail OR u.phoneNumber = :phoneNumber)",
            Parameters
            .with("mail", identifier).and("phoneNumber", identifier)).firstResult()

    }
    fun findIDByReference(reference: String): Long? {
        return find("id WHERE reference = :reference ", mapOf(
            "reference" to reference
        )).project(
            Long::class.java
        ).firstResult()
    }


}