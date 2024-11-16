package pretnders.api.pretnders.persistence.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "trait_pairs")
class TraitPairsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trait_pairs_generator")
    @SequenceGenerator(name = "trait_pairs_generator", sequenceName = "trait_pairs_seq",
        allocationSize = 1)
    @Column(name = "id", updatable = false)
    var id: Long? = null
    @Column(name = "trait_1", columnDefinition = "varchar(50)", nullable = false)
    var trait: String? = null
    @Column(name = "trait_2", columnDefinition = "varchar(50)", nullable = false)
    var mirrorTrait: String? = null
    @Column(name = "description", columnDefinition = "varchar(255)", nullable = false)
    var description: String? = null
    @OneToMany(mappedBy = "trait", fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    @JsonIgnore
    var pretndersTraitPairs: MutableList<PretnderTraitPairsEntity>? = null
    override fun toString(): String {
        return "TraitPairsEntity(id=$id, trait=$trait, mirrorTrait=$mirrorTrait, description=$description)"
    }
}