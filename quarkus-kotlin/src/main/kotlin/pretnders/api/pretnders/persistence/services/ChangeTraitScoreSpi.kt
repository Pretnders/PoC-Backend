package pretnders.api.pretnders.persistence.services

import pretnders.api.pretnders.domain.ports.out.ChangeTraitScoreOut
import pretnders.api.pretnders.persistence.repositories.PretnderTraitPairsCommandsRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class ChangeTraitScoreSpi: ChangeTraitScoreOut {
    @Inject
    private lateinit var pretnderTraitPairsCommandsRepository: PretnderTraitPairsCommandsRepository

    override fun changeScore(traitPairReference: String, score: Short) {
        pretnderTraitPairsCommandsRepository.updateScore(traitPairReference,score)
    }
}