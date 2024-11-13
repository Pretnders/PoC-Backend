package com.pretnders.application.dto.traitpairs

import kotlinx.serialization.Serializable

@Serializable
data class UpdateTraitScoreRequest(val traitPairReference:String, val score:Short)
