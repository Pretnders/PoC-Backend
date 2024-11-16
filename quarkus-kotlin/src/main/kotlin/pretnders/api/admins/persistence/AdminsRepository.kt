package pretnders.api.admins.persistence

import io.quarkus.hibernate.orm.panache.PanacheRepository
import io.quarkus.panache.common.Parameters
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import java.util.*

@ApplicationScoped
@Transactional
class AdminsRepository : PanacheRepository<AdminsEntity?> {

    fun findByIdentifier(identifier: String): Optional<AdminsEntity> {
        return find("SELECT u from AdminsEntity u WHERE (u.mail =:mail OR u.phoneNumber = :phoneNumber)", Parameters
            .with("mail", identifier).and("phoneNumber", identifier))
            .firstResultOptional<AdminsEntity>()

    }
    fun findIDByReference(reference: String): Long {
        return find("id WHERE reference = :reference ", mapOf(
            "reference" to reference
        )).project(
            Long::class.java
        ).firstResult()
    }
    fun updateNickname(mail: String, newNickname: String){
        update("nickname = :newNickname WHERE mail = :mail",
            mapOf(
                "mail" to mail,
                "newNickname" to newNickname
            ))
    }

    fun updateProfilePicture(phoneNumber: String, newProfilePicture: String) {
        update("profilePicture = :profilePictureUrl WHERE phone = :phoneNumber",
            mapOf(
                "phoneNumber" to phoneNumber,
                "profilePictureUrl" to newProfilePicture
            ))
    }
}