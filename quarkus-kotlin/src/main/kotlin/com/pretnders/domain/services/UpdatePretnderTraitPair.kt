package com.pretnders.domain.services

import com.pretnders.domain.ports.`in`.UpdatePretnderTraitPairIn
import com.pretnders.domain.ports.out.UpdatePretnderTraitPairOut
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class UpdatePretnderTraitPair:UpdatePretnderTraitPairIn {
    @Inject
    private lateinit var pretnderTraitPairOut: UpdatePretnderTraitPairOut
    override fun updateScore(traitPairReference: String, score: Short) {
        pretnderTraitPairOut.updateScore(traitPairReference, score)
    }
}