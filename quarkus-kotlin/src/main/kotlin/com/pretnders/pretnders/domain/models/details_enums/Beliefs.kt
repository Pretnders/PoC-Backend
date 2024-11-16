package com.pretnders.pretnders.domain.models.details_enums

import kotlinx.serialization.Serializable

enum class Beliefs (val label: String, val disabled: Boolean) {
    ATHEIST("Athée", false),
    AGNOSTIC("Agnostique", false),
    BUDDHIST("Bouddhiste", false),
    CHRISTIAN("Chrétien.ne", false),
    JEW("Juif.ve", false),
    MUSLIM("Musulman.ne", false),
    PASTAFARISM("Pastafarisme", false);

    companion object {
        fun getOptions():List<BeliefsOption>{
            return entries.map {
                BeliefsOption(it.label, it.name, it.disabled)
            }
        }
    }

    @Serializable
    class BeliefsOption : Options {
        constructor(label: String, name:String, disabled: Boolean) : super(label, name, disabled)
    }
}