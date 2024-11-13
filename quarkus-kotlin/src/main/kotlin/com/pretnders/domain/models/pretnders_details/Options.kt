package com.pretnders.domain.models.pretnders_details

import kotlinx.serialization.Serializable

@Serializable
open class Options(val label:String, val key:String, val disabled: Boolean) {
    override fun toString(): String {
        return "Options(label='$label', key='$key', disabled=$disabled)"
    }
}