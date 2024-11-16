package pretnders.api.pretnders.persistence.services

import pretnders.api.pretnders.domain.ports.out.FindNicknameOut
import pretnders.api.pretnders.persistence.repositories.PretndersQueryRepository
import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject

@RequestScoped
class FindNicknamesSpi : FindNicknameOut {
    @Inject
    private lateinit var pretndersQueryRepository: PretndersQueryRepository

    override fun doNicknameExist(reference:String, newNickname: String): Boolean {
        return pretndersQueryRepository.find(
            "SELECT EXISTS (SELECT p FROM PretndersEntity p WHERE nickname = :nickname AND reference != :reference) " +
                    "as exists",
            mapOf(
                "nickname" to newNickname,
                "reference" to reference
            )
        ).project(Boolean::class.java).firstResult()
    }
}