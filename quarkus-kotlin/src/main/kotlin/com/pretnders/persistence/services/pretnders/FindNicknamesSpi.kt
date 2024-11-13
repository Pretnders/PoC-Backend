package com.pretnders.persistence.services.pretnders

import com.pretnders.domain.ports.out.FindNicknameOut
import com.pretnders.persistence.repositories.PretndersRepository
import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject

@RequestScoped
class FindNicknamesSpi : FindNicknameOut {
    @Inject
    private lateinit var pretndersRepository: PretndersRepository

    override fun doNicknameExist(reference:String, newNickname: String): Boolean {
        return pretndersRepository.find(
            "SELECT EXISTS (SELECT p FROM PretndersEntity p WHERE nickname = :nickname AND reference != :reference) " +
                    "as exists",
            mapOf(
                "nickname" to newNickname,
                "reference" to reference
            )
        ).project(Boolean::class.java).firstResult()
    }
}