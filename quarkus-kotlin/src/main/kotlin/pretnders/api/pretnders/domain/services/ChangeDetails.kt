package pretnders.api.pretnders.domain.services

import pretnders.api.pretnders.domain.models.details_enums.*
import pretnders.api.pretnders.domain.ports.`in`.ChangeDetailsIn
import pretnders.api.pretnders.domain.ports.out.ChangeDetailsOut
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class ChangeDetails(
    private val updatePretenderProfileOut: ChangeDetailsOut
): ChangeDetailsIn {


    override fun changeNickname(reference: String, nickname: String) {
        updatePretenderProfileOut.changeNickname(reference, nickname)
    }

    override fun changeOrientation(reference: String, orientation: SexualOrientations) {
        updatePretenderProfileOut.changeOrientation(reference, orientation.name)
    }

    override fun changeGender(reference: String, gender: Genders) {
        updatePretenderProfileOut.changeGender(reference, gender.name)
    }

    override fun changeHeight(reference: String, newHeight: String) {
        updatePretenderProfileOut.changeHeight(reference, newHeight)
    }

    override fun changeBodyType(reference: String, newBodyType: BodyTypes) {
        updatePretenderProfileOut.changeBodyType(reference, newBodyType.name)
    }

    override fun changeDiet(reference: String, newDiet: Diets) {
        updatePretenderProfileOut.changeDiet(reference, newDiet.name)

    }

    override fun changeBeliefs(reference: String, newBelief: Beliefs) {
        updatePretenderProfileOut.changeBeliefs(reference, newBelief.name)

    }

    override fun changeSmokes(reference: String, newSmokes: Occurrences) {
        updatePretenderProfileOut.changeSmokes(reference, newSmokes.name)

    }

    override fun changeDrinks(reference: String, newDrinks: Occurrences) {
        updatePretenderProfileOut.changeDrinks(reference, newDrinks.name)

    }

    override fun changeSocialStatus(reference: String, newSocialStatus: SocialStatus) {
        updatePretenderProfileOut.changeSocialStatus(reference, newSocialStatus.name)

    }

    override fun changeBiography(reference: String, newBiography: String) {
        updatePretenderProfileOut.changeBiography(reference, newBiography)

    }

    override fun changeCity(reference: String, newCity: String) {
        updatePretenderProfileOut.changeCity(reference, newCity)

    }

    override fun changeCountry(reference: String, newCountry: String) {
        updatePretenderProfileOut.changeCountry(reference, newCountry)

    }

}