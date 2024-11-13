package com.pretnders.domain.services

import com.pretnders.domain.models.pretnders_details.BodyTypes
import com.pretnders.domain.models.pretnders_details.Genders
import com.pretnders.domain.models.pretnders_details.SexualOrientation
import com.pretnders.domain.ports.`in`.UpdatePretnderProfileIn
import com.pretnders.domain.ports.out.UpdatePretnderProfileOut
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class UpdatePretnderProfile: UpdatePretnderProfileIn {

    @Inject
    private lateinit var updatePretenderProfileOut: UpdatePretnderProfileOut

    override fun changeNickname(reference: String, nickname: String) {
        updatePretenderProfileOut.changeNickname(reference, nickname)
    }

    override fun changeOrientation(reference: String, orientation: SexualOrientation) {
        updatePretenderProfileOut.changeOrientation(reference, orientation.name)
    }

    override fun changeGender(reference: String, gender: Genders) {
        Log.info(BodyTypes.getOptions())

        updatePretenderProfileOut.changeGender(reference, gender.name)
    }

    override fun changeHeight(reference: String, newHeight: String) {
        updatePretenderProfileOut.changeHeight(reference, newHeight)
    }

    override fun changeBodyType(reference: String, newBodyType: BodyTypes) {
        updatePretenderProfileOut.changeBodyType(reference, newBodyType.label)
    }

    override fun changeDiet(reference: String, newDiet: String) {
        updatePretenderProfileOut.changeDiet(reference, newDiet)

    }

    override fun changeBeliefs(reference: String, newBelief: String) {
        updatePretenderProfileOut.changeBeliefs(reference, newBelief)

    }

    override fun changeSmokes(reference: String, newSmokes: String) {
        updatePretenderProfileOut.changeSmokes(reference, newSmokes)

    }

    override fun changeDrinks(reference: String, newDrinks: String) {
        updatePretenderProfileOut.changeDrinks(reference, newDrinks)

    }

    override fun changeSocialStatus(reference: String, newSocialStatus: String) {
        updatePretenderProfileOut.changeSocialStatus(reference, newSocialStatus)

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