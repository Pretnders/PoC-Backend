package pretnders.api.pretnders.application.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class ChangeTraitScoreRequest(val traitPairReference:String, val score:Short)
