package com.pretnders.pretnders.domain.ports.out

interface ChangeTraitScoreOut {
    fun updateScore(traitPairReference:String, score:Short)
}