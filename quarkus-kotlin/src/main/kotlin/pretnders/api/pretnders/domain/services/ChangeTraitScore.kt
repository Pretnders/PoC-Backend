package pretnders.api.pretnders.domain.services

import jakarta.enterprise.context.ApplicationScoped
import pretnders.api.pretnders.domain.ports.`in`.ChangeTraitScoreIn
import pretnders.api.pretnders.domain.ports.out.ChangeTraitScoreOut

@ApplicationScoped
class ChangeTraitScore(private val pretnderTraitPairOut: ChangeTraitScoreOut ): ChangeTraitScoreIn {
    override fun updateScore(traitPairReference: String, score: Short) {
        pretnderTraitPairOut.changeScore(traitPairReference, score)
    }
}