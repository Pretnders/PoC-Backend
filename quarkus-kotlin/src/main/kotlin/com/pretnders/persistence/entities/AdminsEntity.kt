package com.pretnders.persistence.entities

import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "admins")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
class AdminsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_generator")
    @SequenceGenerator(name = "users_generator", sequenceName = "users_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    var id: Long? = null
    @Column(name = "nickname", columnDefinition = "varchar(90)", nullable = false, unique = true)
    var nickname: String? = null
    @Column(name = "mail", columnDefinition = "varchar(90)", nullable = false, unique = true)
    var mail: String? = null
    @Column(name = "password", columnDefinition = "bpchar(60)", nullable = false)
    var password: String? = null
    @Column(name = "reference", columnDefinition = "bpchar(32)", nullable = false, unique = true, updatable = false)
    var reference: String? = null
    @Column(name = "phone_number", columnDefinition = "bpchar(10)", unique = true, nullable = false)
    var phoneNumber: String? = null
    override fun toString(): String {
        return "AdminsEntity(id=$id, nickname=$nickname, mail=$mail, password=$password, reference=$reference, phoneNumber=$phoneNumber)"
    }


}