package com.pretnders.persistence.repositories

import com.pretnders.persistence.entities.PretndersEntity
import io.quarkus.hibernate.orm.panache.PanacheRepository
import io.quarkus.panache.common.Parameters
import jakarta.enterprise.context.ApplicationScoped
import java.util.*

@ApplicationScoped
class PretendersRepository : PanacheRepository<PretndersEntity?> {

    fun findByIdentifier(identifier: String): Optional<PretndersEntity> {
        return find("SELECT u from UsersEntity u WHERE (u.mail =:mail OR u.phoneNumber = :phoneNumber)", Parameters.with("mail", identifier).and("phoneNumber", identifier))
            .firstResultOptional<PretndersEntity>()

    }


    fun findIDByReference(reference: String): Long {
        return find("id WHERE reference = :reference ", mapOf(
            "reference" to reference
        )).project(
            Long::class.java
        ).firstResult()
    }
}