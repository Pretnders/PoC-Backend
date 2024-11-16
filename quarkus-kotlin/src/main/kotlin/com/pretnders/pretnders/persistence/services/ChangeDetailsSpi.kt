package com.pretnders.pretnders.persistence.services

import com.pretnders.pretnders.domain.ports.out.ChangeDetailsOut
import com.pretnders.pretnders.persistence.repositories.PretnderDetailsRepository
import com.pretnders.pretnders.persistence.repositories.PretndersRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class ChangeDetailsSpi: ChangeDetailsOut {
    @Inject
    private lateinit var pretndersRepository: PretndersRepository

    @Inject
    private lateinit var pretnderDetailsRepository: PretnderDetailsRepository

    override fun changeHeight(reference: String, newHeight: String) {
        pretnderDetailsRepository.updateHeight(reference, newHeight)
    }

    override fun changeBodyType(reference: String, newBodyType: String) {
        pretnderDetailsRepository.updateBodyType(reference, newBodyType)
    }

    override fun changeDiet(reference: String, newDiet: String) {
        pretnderDetailsRepository.updateDiet(reference, newDiet)
    }

    override fun changeBeliefs(reference: String, newBelief: String) {
        pretnderDetailsRepository.updateBeliefs(reference, newBelief)
    }

    override fun changeSmokes(reference: String, newSmokes: String) {
        pretnderDetailsRepository.updateSmokes(reference, newSmokes)
    }

    override fun changeDrinks(reference: String, newDrinks: String) {
        pretnderDetailsRepository.updateDrinks(reference, newDrinks)
    }

    override fun changeSocialStatus(reference: String, newSocialStatus: String) {
        pretnderDetailsRepository.updateSocialStatus(reference, newSocialStatus)
    }

    override fun changeBiography(reference: String, newBiography: String) {
        pretnderDetailsRepository.updateBiography(reference, newBiography)
    }

    override fun changeCity(reference: String, newCity: String) {
        pretnderDetailsRepository.updateCity(reference, newCity)
    }

    override fun changeCountry(reference: String, newCountry: String) {
        pretnderDetailsRepository.updateCountry(reference, newCountry)
    }

    override fun changeNickname(reference: String, newNickname: String) {
        pretndersRepository.updateNickname(reference, newNickname)
    }

    override fun changeOrientation(reference: String, newOrientation: String) {
        pretnderDetailsRepository.updateOrientation(reference, newOrientation)
    }

    override fun changeGender(reference: String, newGender: String) {
        pretnderDetailsRepository.updateGender(reference, newGender)
    }
}