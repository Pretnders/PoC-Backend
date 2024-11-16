package pretnders.api.pretnders.domain.models


class PretnderDetails {
    var gender: String? = null
    var orientation: String? = null
    var reference: String? = null
    var height: String? = null
    var bodyType: String? = null
    var diet: String? = null
    var beliefs: String? = null
    var smokes: String? = null
    var drinks: String? = null
    var socialStatus: String? = null
    var biography: String? = null
    var city: String? = null
    var country: String? = null
    override fun toString(): String {
        return "PretnderDetails(gender=$gender, orientation=$orientation, reference=$reference, height=$height, bodyType=$bodyType, diet=$diet, beliefs=$beliefs, smokes=$smokes, drinks=$drinks, socialStatus=$socialStatus, biography=$biography, city=$city, country=$country)"
    }
}
