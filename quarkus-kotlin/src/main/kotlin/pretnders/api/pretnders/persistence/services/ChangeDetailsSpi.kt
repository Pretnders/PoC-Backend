package pretnders.api.pretnders.persistence.services

import jakarta.enterprise.context.ApplicationScoped
import pretnders.api.pretnders.domain.ports.out.ChangeDetailsOut
import pretnders.api.pretnders.persistence.repositories.PretnderDetailsCommandsRepository
import pretnders.api.pretnders.persistence.repositories.PretndersCommandsRepository

@ApplicationScoped
class ChangeDetailsSpi(
    private val pretndersCommandsRepository: PretndersCommandsRepository,
    private val pretnderDetailsCommandsRepository: PretnderDetailsCommandsRepository
): ChangeDetailsOut {


    override fun changeHeight(reference: String, newHeight: String) {
        pretnderDetailsCommandsRepository.changeHeight(reference, newHeight)
    }

    override fun changeBodyType(reference: String, newBodyType: String) {
        pretnderDetailsCommandsRepository.changeBodyType(reference, newBodyType)
    }

    override fun changeDiet(reference: String, newDiet: String) {
        pretnderDetailsCommandsRepository.changeDiet(reference, newDiet)
    }

    override fun changeBeliefs(reference: String, newBelief: String) {
        pretnderDetailsCommandsRepository.changeBeliefs(reference, newBelief)
    }

    override fun changeSmokes(reference: String, newSmokes: String) {
        pretnderDetailsCommandsRepository.changeSmokes(reference, newSmokes)
    }

    override fun changeDrinks(reference: String, newDrinks: String) {
        pretnderDetailsCommandsRepository.changeDrinks(reference, newDrinks)
    }

    override fun changeSocialStatus(reference: String, newSocialStatus: String) {
        pretnderDetailsCommandsRepository.changeSocialStatus(reference, newSocialStatus)
    }

    override fun changeBiography(reference: String, newBiography: String) {
        pretnderDetailsCommandsRepository.changeBiography(reference, newBiography)
    }

    override fun changeCity(reference: String, newCity: String) {
        pretnderDetailsCommandsRepository.changeCity(reference, newCity)
    }

    override fun changeCountry(reference: String, newCountry: String) {
        pretnderDetailsCommandsRepository.changeCountry(reference, newCountry)
    }

    override fun changeNickname(reference: String, newNickname: String) {
        pretndersCommandsRepository.changeNickname(reference, newNickname)
    }

    override fun changeOrientation(reference: String, newOrientation: String) {
        pretnderDetailsCommandsRepository.changeOrientation(reference, newOrientation)
    }

    override fun changeGender(reference: String, newGender: String) {
        pretnderDetailsCommandsRepository.changeGender(reference, newGender)
    }
}