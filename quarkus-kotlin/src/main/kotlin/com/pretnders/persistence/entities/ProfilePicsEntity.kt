package com.pretnders.persistence.entities

import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "profile_pics")
class ProfilePicsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_pics_generator")
    @SequenceGenerator(name = "profile_pics_generator", sequenceName = "profile_pics_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    var id: Long? = null

    @Column(name = "url", columnDefinition = "varchar(200)")
    var url: String? = null

    @Column(name = "pic_order")
    @JdbcTypeCode(SqlTypes.SMALLINT)
    var order: Short? = null

    @Column(name = "reference", columnDefinition = "bpchar(32)")
    var reference: String? = null

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "pretenders_id", referencedColumnName = "id")
    var pretnders: PretndersEntity?=null

    override fun toString(): String {
        return "ProfilePicsEntity(id=$id, url=$url, order=$order, reference=$reference, pretnders=$pretnders)"
    }
}