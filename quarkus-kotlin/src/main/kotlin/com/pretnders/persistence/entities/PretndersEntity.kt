package com.pretnders.persistence.entities

import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "pretenders")
class PretndersEntity {
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
    @OneToOne(mappedBy = "pretndersEntity", fetch = FetchType.LAZY)
    var pretenderDetails: PretendersDetailsEntity? = null
    @OneToMany(mappedBy = "pretnders", fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST])
    var profilePics: MutableList<ProfilePicsEntity>? = null



    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PretndersEntity) return false

        if (id != other.id) return false
        if (nickname != other.nickname) return false
        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (mail != other.mail) return false
        if (password != other.password) return false
        if (reference != other.reference) return false
        if (device != other.device) return false
        if (phoneNumber != other.phoneNumber) return false
        if (verificationCode != other.verificationCode) return false
        if (verificationCodeTimestamp != other.verificationCodeTimestamp) return false
        if (passwordVerificationCode != other.passwordVerificationCode) return false
        if (passwordVerificationTimestamp != other.passwordVerificationTimestamp) return false
        if (accountVerifiedStatus != other.accountVerifiedStatus) return false
        if (pretenderDetails != other.pretenderDetails) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (nickname?.hashCode() ?: 0)
        result = 31 * result + (firstName?.hashCode() ?: 0)
        result = 31 * result + (lastName?.hashCode() ?: 0)
        result = 31 * result + (mail?.hashCode() ?: 0)
        result = 31 * result + (password?.hashCode() ?: 0)
        result = 31 * result + (reference?.hashCode() ?: 0)
        result = 31 * result + (device?.hashCode() ?: 0)
        result = 31 * result + (phoneNumber?.hashCode() ?: 0)
        result = 31 * result + (verificationCode?.hashCode() ?: 0)
        result = 31 * result + (verificationCodeTimestamp?.hashCode() ?: 0)
        result = 31 * result + (passwordVerificationCode?.hashCode() ?: 0)
        result = 31 * result + (passwordVerificationTimestamp?.hashCode() ?: 0)
        result = 31 * result + (accountVerifiedStatus?.hashCode() ?: 0)
        result = 31 * result + (pretenderDetails?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "PretndersEntity(id=$id, nickname=$nickname, firstName=$firstName, lastName=$lastName, mail=$mail, password=$password, reference=$reference, device=$device, phoneNumber=$phoneNumber, verificationCode=$verificationCode, verificationCodeTimestamp=$verificationCodeTimestamp, passwordVerificationCode=$passwordVerificationCode, passwordVerificationTimestamp=$passwordVerificationTimestamp, accountVerifiedStatus=$accountVerifiedStatus, pretenderDetails=$pretenderDetails, profilePics=$profilePics)"
    }


}