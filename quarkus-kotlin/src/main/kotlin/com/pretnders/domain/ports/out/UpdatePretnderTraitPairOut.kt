package com.pretnders.domain.ports.out

interface UpdatePretnderTraitPairOut {
    fun updateScore(traitPairReference:String, score:Short)
}