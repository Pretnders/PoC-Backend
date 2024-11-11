package com.pretnders.domain.models.pretnders


class PretnderDetails {
    var height: Int? = null
    var bodyType: Int? = null
    var diet: String? = null
    var beliefs: String? = null
    var smokes: String? = null
    var drinks: String? = null
    var socialStatus: String? = null
    var biography: String? = null
    var city: String? = null
    var country: String? = null
    override fun toString(): String {
        return "PretnderDetails(height=$height, bodyType=$bodyType, diet=$diet, beliefs=$beliefs, smokes=$smokes, drinks=$drinks, socialStatus=$socialStatus, biography=$biography, city=$city, country=$country)"
    }
}
