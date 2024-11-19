package pretnders.api.pretnders.domain.models.details_enums

import kotlinx.serialization.Serializable

enum class Genders(
    val label: String,
    val disabled:Boolean
) {
    CISMALE("Homme cis", false),
    CISFEMALE("Femme cis", false),
    FEMALE("Femme", false),
    MALE("Homme", false),
    ANDROGYNE("Androgyne", false),
    NONBINARY("Non-binaire", false),
    TRANSMACULINE("Transmasc", false),
    TRANSFEMININE("Transfem", false),
    GENDERFLUID("Genderfluid", disabled = false),
    NC("Non connu", false);

    companion object {
        fun getOptions():List<GenderOption>{
            return entries.map {
                GenderOption(it.label, it.name, it.disabled)
            }
        }
    }

    @Serializable
    class GenderOption : Options {
        constructor(label: String, key:String, disabled: Boolean) : super(label, key, disabled)
    }
}