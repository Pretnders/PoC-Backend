package com.pretnders.pretnders.domain.models.details_enums

import kotlinx.serialization.Serializable

enum class BodyTypes(val label: String, val disabled: Boolean) {
    THIN("Fin", false),
    SLIM("Mince", false),
    ATHLETIC("Athlétique", false),
    LEAN("Svelte", false),
    NORMAL("Normal", false),
    HOURGLASS("Sablier", false),
    THICK("Pulpeux.se", false),
    FULL_FIGURED("Rond.e", false),
    BROAD("Large", false),
    NC("Non connu", true);

    companion object {
        fun getOptions():List<BodyTypesOption>{
            return entries.map {
                BodyTypesOption(it.label, it.name, it.disabled)
            }
        }
    }

    @Serializable
    class BodyTypesOption : Options {
        constructor(label: String, name:String, disabled: Boolean) : super(label, name, disabled)
    }
}