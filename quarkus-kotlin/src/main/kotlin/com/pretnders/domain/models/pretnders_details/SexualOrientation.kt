package com.pretnders.domain.models.pretnders_details

import kotlinx.serialization.Serializable

@Serializable
enum class SexualOrientation(val label: String, val disabled: Boolean) {
    HETEROSEXUAL("Hétérosexuel.le", false),
    HOMOSEXUAL("Homosexuel.le", false),
    BISEXUAL("Bisexuel.le", false),
    ASEXUAL("Asexuel.le", false),
    HOMOFLEXIBLE("Homoflexible", false),
    HETEROFLEXIBLE("Hétéroflexible", false),
    FURRIES("Degen (Furries)", false),
    NC("Non connu", false);

    companion object {
        fun getOptions(): List<SexualOrientationOptions> {
            return entries.map {
                SexualOrientationOptions(it.label, it.name, it.disabled)
            }.toList()
        }
    }

    @Serializable
    class SexualOrientationOptions : Options {
        constructor(label: String, name: String, disabled: Boolean) : super(label, name, disabled)
    }
}