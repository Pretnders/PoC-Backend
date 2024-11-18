package pretnders.api.pretnders.domain.models.details_enums

import kotlinx.serialization.Serializable

@Serializable
enum class SexualOrientations(val label: String, val disabled: Boolean) {
    HETEROSEXUAL("Hétérosexuel.le", false),
    HOMOSEXUAL("Homosexuel.le", false),
    BISEXUAL("Bisexuel.le", false),
    ASEXUAL("Asexuel.le", false),
    HOMOFLEXIBLE("Homoflexible", false),
    HETEROFLEXIBLE("Hétéroflexible", false),
    FURRIES("Degen (Furries)", false),
    NC("Non connu", false);

    companion object {
        fun getOptions(): List<SexualOrientationOption> {
            return entries.map {
                SexualOrientationOption(it.label, it.name, it.disabled)
            }
        }
    }

    @Serializable
    class SexualOrientationOption : Options {
        constructor(label: String, key: String, disabled: Boolean) : super(label, key, disabled)
    }
}