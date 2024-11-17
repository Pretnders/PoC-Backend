package pretnders.api.pretnders.domain.models.details_enums

import kotlinx.serialization.Serializable

enum class RelationshipTypes (val label: String, val disabled: Boolean){
    MONOAMOROUS("Monoamoureux.se", false),
    POLYAMOROUS("Polyamoureux.se", false),
    NON_EXCLUSIVE("Relation libre", false),
    FRIENDSHIP("Amitié", false),
    VIRTUAL_FRIENDSHIPS("Amitié virtuelle", false),
    FRIENDS_WITH_BENEFITS("Amis avec avantages", false),
    NON_PROVIDED("Non renseigné", false);

    companion object {
        fun getOptions():List<RelationshipOptions>{
            return entries.map {
                RelationshipOptions(it.label, it.name, it.disabled)
            }
        }
    }

    @Serializable
    class RelationshipOptions : Options {
        constructor(label: String, name:String, disabled: Boolean) : super(label, name, disabled)
    }
}