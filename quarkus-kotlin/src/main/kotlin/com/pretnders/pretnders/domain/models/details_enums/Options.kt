package com.pretnders.pretnders.domain.models.details_enums

import kotlinx.serialization.Serializable

@Serializable
open class Options(val label:String, val key:String, val disabled: Boolean) {
    override fun toString(): String {
        return "Options(label='$label', key='$key', disabled=$disabled)"
    }
}