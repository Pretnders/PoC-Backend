package pretnders.api.pretnders.domain.services

import pretnders.api.pretnders.domain.ports.`in`.ChangeTraitScoreIn
import pretnders.api.pretnders.domain.ports.out.ChangeTraitScoreOut
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class ChangeTraitScore: ChangeTraitScoreIn {
    @Inject
    private lateinit var pretnderTraitPairOut: ChangeTraitScoreOut
    override fun updateScore(traitPairReference: String, score: Short) {
        pretnderTraitPairOut.changeScore(traitPairReference, score)
    }
}