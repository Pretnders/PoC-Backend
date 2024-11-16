package com.pretnders.pretnders.persistence.entities

import com.pretnders.profile_pictures.persistence.entities.ProfilePicsEntity
import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "pretenders")
class PretndersEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pretenders_generator")
    @SequenceGenerator(name = "pretenders_generator", sequenceName = "pretenders_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    var id: Long? = null
    @Column(name = "nickname", columnDefinition = "varchar(30)", nullable = false)
    var nickname: String? = null
    @Column(name = "first_name", columnDefinition = "varchar(30)", nullable = false)
    var firstName: String? = null
    @Column(name = "last_name", columnDefinition = "varchar(30)", nullable = false)
    var lastName: String? = null
    @Column(name = "mail", columnDefinition = "varchar(90)", nullable = false, unique = true)
    var mail: String? = null
    @Column(name = "password", columnDefinition = "bpchar(60)", nullable = false)
    var password: String? = null
    @Column(name = "reference", columnDefinition = "bpchar(32)", nullable = false, unique = true, updatable = false)
    var reference: String? = null
    @Column(name = "device", columnDefinition = "varchar(200)", nullable = false, unique = true, updatable = false)
    var device: String? = "DEVICE"
    @Column(name = "phone_number", columnDefinition = "bpchar(10)", unique = true, nullable = false)
    var phoneNumber: String? = null
    @Column(name = "verification_code", columnDefinition = "varchar(100)")
    var verificationCode: String? = null
    @Column(name = "verification_code_timestamp", columnDefinition = "TIMESTAMP")
    var verificationCodeTimestamp: Timestamp? = null
    @Column(name = "password_verification_code", columnDefinition = "varchar(100)")
    var passwordVerificationCode: String? = null
    @Column(name = "password_verification_timestamp", columnDefinition = "TIMESTAMP")
    var passwordVerificationTimestamp: Timestamp? = null
    @Column(name = "account_verified", columnDefinition = "boolean DEFAULT false", nullable = false)
    var accountVerifiedStatus: Boolean? = false
    @OneToOne(mappedBy = "pretnder", fetch = FetchType.LAZY)
    var pretnderDetails: PretnderDetailsEntity? = null
    @OneToMany(mappedBy = "pretnder", cascade = [CascadeType.PERSIST], fetch = FetchType.LAZY)
    @OrderBy("order ASC")
    var profilePictures: MutableList<ProfilePicsEntity>? = null
    @OneToMany(mappedBy = "pretnder", cascade = [CascadeType.PERSIST], fetch = FetchType.LAZY)
    var traitPairs: MutableList<PretnderTraitPairsEntity>? = null

    override fun toString(): String {
        return "PretndersEntity(id=$id, nickname=$nickname, firstName=$firstName, lastName=$lastName, mail=$mail, " +
                "password=$password, reference=$reference, device=$device, phoneNumber=$phoneNumber, " +
                "verificationCode=$verificationCode, verificationCodeTimestamp=$verificationCodeTimestamp, " +
                "passwordVerificationCode=$passwordVerificationCode, " +
                "passwordVerificationTimestamp=$passwordVerificationTimestamp, " +
                "accountVerifiedStatus=$accountVerifiedStatus, pretnderDetails=$pretnderDetails, " +
                "profilePictures=$profilePictures, pretnderTraitPairs=$traitPairs)"
    }
}