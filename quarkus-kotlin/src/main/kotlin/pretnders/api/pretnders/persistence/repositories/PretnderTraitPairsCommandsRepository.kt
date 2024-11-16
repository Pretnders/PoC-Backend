package pretnders.api.pretnders.persistence.repositories

import pretnders.api.pretnders.persistence.entities.PretnderTraitPairsEntity
import io.quarkus.hibernate.orm.panache.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional


@ApplicationScoped
class PretnderTraitPairsCommandsRepository : PanacheRepository<PretnderTraitPairsEntity?> {

    @Transactional
    fun updateScore(traitPairReference: String, score: Short) {
        update("score = :score WHERE reference = :reference", mapOf(
            "reference" to traitPairReference,
            "score" to score
        ))
    }
}