package com.pretnders.pretnders.domain.ports.`in`

interface ChangeTraitScoreIn {
    fun updateScore(traitPairReference:String, score:Short)
}