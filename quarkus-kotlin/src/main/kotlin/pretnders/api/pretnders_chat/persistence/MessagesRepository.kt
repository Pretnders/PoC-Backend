package pretnders.api.pretnders_chat.persistence

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class MessagesRepository: PanacheRepository<MessagesEntity> {
    @Transactional
    fun add(toPersist: MessagesEntity) {
        persistAndFlush(toPersist)
    }

    fun findAllReports(): List<MessagesEntity> {
        return find("SELECT m FROM MessagesEntity m" +
                "WHERE m.reported = true").list()
    }

    @Transactional
    fun reportMessage(reference:String){
        update(
        "reported = true where reference = :reference",
        mapOf(
            "reference" to reference
        ))
    }

}
