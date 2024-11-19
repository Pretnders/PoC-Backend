package pretnders.api.pretnders.persistence.repositories

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import pretnders.api.pretnders.persistence.entities.PretndersEntity

@ApplicationScoped
class PretndersNicknamesQueryRepository:PanacheRepository<PretndersEntity> {
    fun doNicknameExist(reference:String, newNickname: String): Boolean {
        return find(
            "SELECT EXISTS (SELECT p FROM PretndersEntity p WHERE nickname = :nickname AND reference != :reference) " +
                    "as exists",
            mapOf(
                "nickname" to newNickname,
                "reference" to reference
            )
        ).project(Boolean::class.java).firstResult()!!
    }

    fun doNicknameExist( newNickname: String): Boolean {
        return find(
            "SELECT EXISTS (SELECT p FROM PretndersEntity p WHERE nickname = :nickname) " +
                    "as exists",
            mapOf(
                "nickname" to newNickname
            )
        ).project(Boolean::class.java).firstResult()!!
    }
}