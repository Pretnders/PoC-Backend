package pretnders.api.pretnders.application.dto.responses

import pretnders.api.pretnders.domain.models.details_enums.*
import kotlinx.serialization.Serializable

@Serializable
data class FindDetailsOptionsResponse(
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
