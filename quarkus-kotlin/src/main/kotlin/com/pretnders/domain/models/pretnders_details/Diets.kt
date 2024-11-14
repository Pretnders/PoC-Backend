package com.pretnders.domain.models.pretnders_details

import kotlinx.serialization.Serializable

enum class Diets (val label: String, val disabled: Boolean) {
    VEGAN("Vegan.ne", false),
    VEGETARIAN("Végétarien.ne", false),
    GLUTENFREE("Sans gluten", false),
    PISCIVORE("Piscivore", false),
    HALAL("Halal", false),
    KASHER("Kasher", false),
    OMNIVORE("Omnivore", false);

    companion object {
        fun getOptions():List<DietsOption>{
            return entries.map {
                DietsOption(it.label, it.name, it.disabled)
            }
        }
    }

    @Serializable
    class DietsOption : Options {
        constructor(label: String, name:String, disabled: Boolean) : super(label, name, disabled)
    }
}