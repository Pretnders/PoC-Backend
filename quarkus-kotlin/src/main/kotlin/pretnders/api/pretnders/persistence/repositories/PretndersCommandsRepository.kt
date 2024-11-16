package pretnders.api.pretnders.persistence.repositories

import pretnders.api.pretnders.persistence.entities.PretndersEntity
import io.quarkus.hibernate.orm.panache.PanacheRepository
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
@Transactional
class PretndersCommandsRepository: PanacheRepository<PretndersEntity?> {
    fun changeNickname(reference: String, newNickname: String) {
        Log.info("Nickname $newNickname reference $reference")
        update(
            "nickname = :newNickname WHERE reference = :reference ", mapOf(
                "reference" to reference,
                "newNickname" to newNickname
            )
        )
    }
}