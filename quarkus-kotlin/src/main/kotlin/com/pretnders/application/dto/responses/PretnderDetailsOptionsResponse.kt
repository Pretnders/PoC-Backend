package com.pretnders.application.dto.responses

import com.pretnders.domain.models.pretnders_details.*
import kotlinx.serialization.Serializable

@Serializable
data class PretnderDetailsOptionsResponse(
    val beliefs: List<Beliefs.BeliefsOption>,
    val bodyTypes: List<BodyTypes.BodyTypesOption>,
    val diets: List<Diets.DietsOption>,
    val socialStatus: List<SocialStatus.SocialStatusOption>,
    val occurrences: List<Occurrences.OccurrencesOption>,
    val genders: List<Genders.GenderOption>,
    val orientations: List<SexualOrientations.SexualOrientationOption>,
    ){
    override fun toString(): String {
        return "PretnderDetailsOptionsResponse(beliefs=$beliefs, bodyTypes=$bodyTypes, diets=$diets, socialStatus=$socialStatus, occurrences=$occurrences, genders=$genders, orientations=$orientations)"
    }
}
