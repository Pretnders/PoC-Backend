package pretnders.api.pretnders.persistence.repositories

import pretnders.api.pretnders.persistence.entities.PretndersEntity
import io.quarkus.hibernate.orm.panache.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import java.util.*

@ApplicationScoped
class PretndersQueryRepository : PanacheRepository<PretndersEntity?> {

    fun findByIdentifier(identifier: String): Optional<PretndersEntity> {
        return find("LEFT JOIN FETCH traitPairs tp "+
                "LEFT JOIN FETCH tp.trait tpt "+
                "LEFT JOIN FETCH pretnderDetails pd "+
            "WHERE (mail =:mail OR phoneNumber = :phoneNumber) ORDER BY tpt.trait ASC",
            mapOf(
                "mail" to identifier,
                "phoneNumber" to identifier
            )
        )
            .firstResultOptional<PretndersEntity>()
    }

    fun findIDByReference(reference: String): Long {
        return find(
            "SELECT id FROM PretndersEntity WHERE reference = :reference ", mapOf(
                "reference" to reference
            )
        ).project(
            Long::class.java
        ).firstResult()
    }
}