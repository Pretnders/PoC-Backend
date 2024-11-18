package pretnders.api.pretnders.persistence.services

import jakarta.enterprise.context.ApplicationScoped
import pretnders.api.pretnders.domain.ports.out.ChangeTraitScoreOut
import pretnders.api.pretnders.persistence.repositories.PretnderTraitPairsCommandsRepository

@ApplicationScoped
class ChangeTraitScoreSpi(
    private val pretnderTraitPairsCommandsRepository: PretnderTraitPairsCommandsRepository
): ChangeTraitScoreOut {

    override fun changeScore(traitPairReference: String, score: Short) {
        pretnderTraitPairsCommandsRepository.updateScore(traitPairReference,score)
    }
}