package com.pretnders.persistence.repositories

import com.pretnders.persistence.entities.MessagesEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import java.sql.Timestamp
import java.time.Instant

@ApplicationScoped
class MessagesRepository:PanacheRepository<MessagesEntity> {
    @Transactional
    fun add(toPersist: MessagesEntity, receiver:String, sender:String): Long {
        toPersist.match = 1L
        toPersist.timeSamp = Timestamp.from(Instant.now())
        persistAndFlush(toPersist)
        return toPersist.id!!
    }

    fun findAllReports(): List<MessagesEntity> {
        return find("SELECT m FROM MessagesEntity m" +
                "WHERE m.reported = true").list()
    }

}
