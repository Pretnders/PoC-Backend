package pretnders.api.pretnders.domain.ports.out

interface ChangeTraitScoreOut {
    fun changeScore(traitPairReference:String, score:Short)
}