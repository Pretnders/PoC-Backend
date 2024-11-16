package com.pretnders.pretnders.domain.services

import com.pretnders.pretnders.domain.ports.`in`.ChangeTraitScoreIn
import com.pretnders.pretnders.domain.ports.out.ChangeTraitScoreOut
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class ChangeTraitScore: ChangeTraitScoreIn {
    @Inject
    private lateinit var pretnderTraitPairOut: ChangeTraitScoreOut
    override fun updateScore(traitPairReference: String, score: Short) {
        pretnderTraitPairOut.updateScore(traitPairReference, score)
    }
}