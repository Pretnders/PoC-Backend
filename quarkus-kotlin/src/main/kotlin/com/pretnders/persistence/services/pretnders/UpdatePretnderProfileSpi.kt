package com.pretnders.persistence.services.pretnders

import com.pretnders.domain.ports.out.UpdatePretnderProfileOut
import com.pretnders.persistence.repositories.PretnderDetailsRepository
import com.pretnders.persistence.repositories.PretndersRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional

@ApplicationScoped
class UpdatePretnderProfileSpi:UpdatePretnderProfileOut {
    @Inject
    private lateinit var pretndersRepository: PretndersRepository

    @Inject
    private lateinit var pretnderDetailsRepository: PretnderDetailsRepository
    override fun changeNickname(reference: String, newNickname: String) {
        pretndersRepository.updateNickname(reference, newNickname)
    }

    @Transactional
    override fun changeOrientation(reference: String, newOrientation: String) {
        pretnderDetailsRepository.updateOrientation(reference, newOrientation)
    }
}