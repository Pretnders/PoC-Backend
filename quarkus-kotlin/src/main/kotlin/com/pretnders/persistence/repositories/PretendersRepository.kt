package com.pretnders.persistence.repositories

import com.pretnders.persistence.entities.PretendersEntity
import io.quarkus.hibernate.orm.panache.PanacheRepository
import io.quarkus.panache.common.Parameters
import jakarta.enterprise.context.ApplicationScoped
import java.util.*

@ApplicationScoped
class PretendersRepository : PanacheRepository<PretendersEntity?> {

    fun findByIdentifier(identifier: String): Optional<PretendersEntity> {
        return find("SELECT u from UsersEntity u WHERE (u.mail =:mail OR u.phoneNumber = :phoneNumber)", Parameters.with("mail", identifier).and("phoneNumber", identifier))
            .firstResultOptional<PretendersEntity>()

    }
}