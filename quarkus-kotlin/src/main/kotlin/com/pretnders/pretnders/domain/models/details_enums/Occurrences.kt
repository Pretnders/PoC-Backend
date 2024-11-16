package com.pretnders.pretnders.domain.models.details_enums

import kotlinx.serialization.Serializable

enum class Occurrences(val label: String, val disabled: Boolean){
    NEVER("Jamais", false),
    RARELY("Rarement", false),
    PARTY("En soirée", false),
    SOCIALLY("En société", false),
    ONCE_IN_A_WHILE("De temps en temps", false),
    OFTEN("Souvent", false),
    DAILY("Quotidiennement", false);

    companion object {
        fun getOptions():List<OccurrencesOption>{
            return entries.map {
                OccurrencesOption(it.label, it.name, it.disabled)
            }
        }
    }

    @Serializable
    class OccurrencesOption : Options {
        constructor(label: String, name:String, disabled: Boolean) : super(label, name, disabled)
    }
}
