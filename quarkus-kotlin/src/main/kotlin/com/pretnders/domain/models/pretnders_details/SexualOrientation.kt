package com.pretnders.domain.models.pretnders_details

import kotlinx.serialization.Serializable

@Serializable
enum class SexualOrientation(val label: String) {
    HOMOFLEXIBLE("Homoflexible"),
    HETEROFLEXIBLE("Hétéroflexible"),
    HETEROSEXUAL("Hétérosexuel.le"),
    HOMOSEXUAL("Homosexuel.le"),
    BISEXUAL("Bisexuel.le"),
    ASEXUAL("Asexuel.le"),
    FURRIES("Degen (Furries)");
}