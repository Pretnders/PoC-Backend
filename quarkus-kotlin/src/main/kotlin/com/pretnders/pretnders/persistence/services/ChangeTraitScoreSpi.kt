package com.pretnders.pretnders.persistence.services

import com.pretnders.pretnders.domain.ports.out.ChangeTraitScoreOut
import com.pretnders.pretnders.persistence.repositories.PretnderTraitPairsRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional

@ApplicationScoped
class ChangeTraitScoreSpi: ChangeTraitScoreOut {
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