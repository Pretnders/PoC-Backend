package com.pretnders.persistence.services.pretnders

import com.pretnders.domain.ports.out.UpdatePretnderTraitPairOut
import com.pretnders.persistence.repositories.PretnderTraitPairsRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional

@ApplicationScoped
class UpdatePretnderTraitPairSpi:UpdatePretnderTraitPairOut {
    @Inject
    private lateinit var pretnderTraitPairsRepository: PretnderTraitPairsRepository

    @Transactional
    override fun updateScore(traitPairReference: String, score: Short) {
        pretnderTraitPairsRepository.update("score = :score WHERE reference = :reference", mapOf(
            "reference" to traitPairReference,
            "score" to score
        ))
    }
}