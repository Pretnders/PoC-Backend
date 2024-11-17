package pretnders.api.admins.persistence;

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional

@ApplicationScoped
class AdminsCommandsRepository : PanacheRepository<AdminsEntity> {
    @Transactional
    fun updateNickname(mail: String, newNickname: String){
        update("nickname = :newNickname WHERE mail = :mail",
            mapOf(
                "mail" to mail,
                "newNickname" to newNickname
            ))
    }

    @Transactional
    fun updateProfilePicture(phoneNumber: String, newProfilePicture: String) {
        update("profilePicture = :profilePictureUrl WHERE phone = :phoneNumber",
            mapOf(
                "phoneNumber" to phoneNumber,
                "profilePictureUrl" to newProfilePicture
            ))
    }
}
