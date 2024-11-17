package pretnders.api.pretnders.persistence.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "pretnder_trait_pairs")
class PretnderTraitPairsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pretender_trait_pairs_generator")
    @SequenceGenerator(name = "pretender_trait_pairs_generator", sequenceName = "pretnder_trait_pairs_seq",
        allocationSize = 1)
    @Column(name = "id", updatable = false)
    var id: Long? = null
    @Column(name = "reference", columnDefinition = "bpchar(32)", updatable = false, nullable = false, unique = true)
    var reference: String? = null
    @Column(name = "score",  nullable = false)
    @JdbcTypeCode(SqlTypes.SMALLINT)
    var score: Short? = 50
    @ManyToOne(cascade = [(CascadeType.MERGE)])
    @JoinColumn(name = "pretnder_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    var pretnder: PretndersEntity? = null
    @ManyToOne(cascade = [(CascadeType.MERGE)], fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "trait_pairs_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    var trait: TraitPairsEntity? = null
    override fun toString(): String {
        return "PretnderTraitPairsEntity(score=$score, reference=$reference, id=$id, trait=$trait)"
    }

}