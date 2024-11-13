package com.pretnders.domain.models.pretnders_details

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
        fun getOptions():List<BodyTypesOptions>{
            return entries.map {
                BodyTypesOptions(it.label, it.name, it.disabled)
            }.toList()
        }
    }

    @Serializable
    class BodyTypesOptions : Options {
        constructor(label: String, name:String, disabled: Boolean) : super(label, name, disabled)
    }
}