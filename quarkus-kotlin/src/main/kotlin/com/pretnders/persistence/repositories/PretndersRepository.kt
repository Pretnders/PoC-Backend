package com.pretnders.persistence.repositories

import com.pretnders.persistence.entities.PretndersEntity
import io.quarkus.hibernate.orm.panache.PanacheRepository
import io.quarkus.logging.Log
import io.quarkus.panache.common.Parameters
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.EntityGraph
import jakarta.transaction.Transactional
import java.util.*

@ApplicationScoped
class PretndersRepository : PanacheRepository<PretndersEntity?> {

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

    @Transactional
    fun updateNickname(reference: String, newNickname: String) {
        Log.info("Nickname $newNickname reference $reference")
        update(
            "nickname = :newNickname WHERE reference = :reference ", mapOf(
                "reference" to reference,
                "newNickname" to newNickname
            )
        )
    }
}