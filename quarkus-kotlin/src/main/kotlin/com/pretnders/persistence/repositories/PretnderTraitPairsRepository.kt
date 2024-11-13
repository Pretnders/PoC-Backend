package com.pretnders.persistence.repositories

import com.pretnders.persistence.entities.PretnderTraitPairsEntity
import com.pretnders.persistence.models.PretnderTraitPair
import io.quarkus.hibernate.orm.panache.PanacheRepository
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped



@ApplicationScoped
class PretnderTraitPairsRepository : PanacheRepository<PretnderTraitPairsEntity?> {

    fun findMyTraitPairs(pretnderReference: String): List<PretnderTraitPair> {
        val res = find(
            "SELECT ptp.reference, ptp.traitPairs.trait, ptp.traitPairs.mirrorTrait, ptp.traitPairs.description, ptp.score FROM PretnderTraitPairsEntity ptp" +
                    " WHERE ptp.pretnder.reference = :reference",
            mapOf("reference" to pretnderReference)
        ).project(PretnderTraitPair::class.java).list<PretnderTraitPair>()
        Log.info(res.toString())
        return res
    }

}