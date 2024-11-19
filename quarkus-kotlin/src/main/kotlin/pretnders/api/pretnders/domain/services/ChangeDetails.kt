package pretnders.api.pretnders.domain.services

import pretnders.api.pretnders.domain.models.details_enums.*
import pretnders.api.pretnders.domain.ports.`in`.ChangeDetailsIn
import pretnders.api.pretnders.domain.ports.out.ChangeDetailsOut
import jakarta.enterprise.context.ApplicationScoped
import pretnders.api.shared.errors.ApplicationException
import pretnders.api.shared.errors.ApplicationExceptionsEnum
import pretnders.api.shared.utils.validators.InputsValidator.validateHeight

@ApplicationScoped
class ChangeDetails(
    private val changeDetailsOut: ChangeDetailsOut
): ChangeDetailsIn {


    override fun changeNickname(reference: String, nickname: String) {
        if(nickname.length < 3){
            throw ApplicationException(ApplicationExceptionsEnum.ERROR)
        }
        changeDetailsOut.changeNickname(reference, nickname)
    }

    override fun changeOrientation(reference: String, orientation: SexualOrientations) {
        changeDetailsOut.changeOrientation(reference, orientation.name)
    }

    override fun changeGender(reference: String, gender: Genders) {
        changeDetailsOut.changeGender(reference, gender.name)
    }

    override fun changeHeight(reference: String, newHeight: String) {
        validateHeight(newHeight)
        changeDetailsOut.changeHeight(reference, newHeight)
    }

    override fun changeBodyType(reference: String, newBodyType: BodyTypes) {
        changeDetailsOut.changeBodyType(reference, newBodyType.name)
    }

    override fun changeDiet(reference: String, newDiet: Diets) {
        changeDetailsOut.changeDiet(reference, newDiet.name)

    }

    override fun changeBeliefs(reference: String, newBelief: Beliefs) {
        changeDetailsOut.changeBeliefs(reference, newBelief.name)

    }

    override fun changeSmokes(reference: String, newSmokes: Occurrences) {
        changeDetailsOut.changeSmokes(reference, newSmokes.name)

    }

    override fun changeDrinks(reference: String, newDrinks: Occurrences) {
        changeDetailsOut.changeDrinks(reference, newDrinks.name)

    }

    override fun changeSocialStatus(reference: String, newSocialStatus: SocialStatus) {
        changeDetailsOut.changeSocialStatus(reference, newSocialStatus.name)

    }

    override fun changeBiography(reference: String, newBiography: String) {
        changeDetailsOut.changeBiography(reference, newBiography)

    }

    override fun changeCity(reference: String, newCity: String) {
        changeDetailsOut.changeCity(reference, newCity)

    }

    override fun changeCountry(reference: String, newCountry: String) {
        changeDetailsOut.changeCountry(reference, newCountry)

    }

}