package com.pretnders.persistence.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "pretender_details")
class PretndersDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pretenders_details_generator")
    @SequenceGenerator(name = "pretenders_details_generator", sequenceName = "pretenders_details_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    var id: Long? = null
    @Column(name="gender", columnDefinition = "varchar(20)", nullable = false)
    var gender: String? = null
    @Column(name="sexual_orientation", columnDefinition = "varchar(35)", nullable = false)
    var orientation: String? = null
    @Column(name="height", columnDefinition = "bpchar(3)", updatable = false, nullable = false)
    var height: String? = null
    @Column(name="reference", columnDefinition = "bpchar(31)", updatable = false, nullable = false, unique = true)
    var reference: String? = null
    @Column(name="body_type", columnDefinition = "varchar(12)", updatable = false, nullable = false)
    var bodyType: String? = null
    @Column(name="diet", columnDefinition = "varchar(20)", nullable = false)
    var diet: String? = null
    @Column(name="beliefs", columnDefinition = "varchar(20)", nullable = false)
    var beliefs: String? = null
    @Column(name="smokes", columnDefinition = "varchar(12)", nullable = false)
    var smokes: String? = null
    @Column(name="drinks", columnDefinition = "varchar(12)", nullable = false)
    var drinks: String? = null
    @Column(name="social_status", columnDefinition = "varchar(12)", nullable = false)
    var socialStatus: String? = null
    @Column(name="biography", columnDefinition = "TEXT", nullable = false)
    var biography: String? = null
    @Column(name="city", columnDefinition = "varchar(45)", nullable = false)
    var city: String? = null
    @Column(name="country", columnDefinition = "varchar(20)", nullable = false)
    var country: String? = null
    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "pretender_id", referencedColumnName = "id", nullable = false, updatable = false)
    @JsonIgnore
    private val pretndersEntity: PretndersEntity? = null
    override fun toString(): String {
        return "PretndersDetailsEntity(country=$country, city=$city, biography=$biography, socialStatus=$socialStatus, drinks=$drinks, smokes=$smokes, beliefs=$beliefs, diet=$diet, bodyType=$bodyType, reference=$reference, height=$height, orientation=$orientation, gender=$gender, id=$id)"
    }

}
