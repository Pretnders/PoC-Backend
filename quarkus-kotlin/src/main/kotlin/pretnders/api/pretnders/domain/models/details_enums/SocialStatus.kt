package pretnders.api.pretnders.domain.models.details_enums

import kotlinx.serialization.Serializable

enum class SocialStatus (val label: String, val disabled: Boolean) {
    STUDENT("Étudiant.e", false),
    EMPLOYED("Employé", false),
    INACTIVE("Sans activité", false);

    companion object {
        fun getOptions():List<SocialStatusOption>{
            return entries.map {
                SocialStatusOption(it.label, it.name, it.disabled)
            }
        }
    }

    @Serializable
    class SocialStatusOption : Options {
        constructor(label: String, key:String, disabled: Boolean) : super(label, key, disabled)
    }
}