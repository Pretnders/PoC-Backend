package com.pretnders.pretnders.domain.ports.`in`

import com.pretnders.pretnders.domain.models.details_enums.*

interface ChangeDetailsIn {
    fun changeNickname(reference:String, nickname: String)
    fun changeOrientation(reference:String, orientation: SexualOrientations)
    fun changeGender(reference:String, gender: Genders)
    fun changeHeight(reference:String, newHeight:String)
    fun changeBodyType(reference:String, newBodyType: BodyTypes)
    fun changeDiet(reference:String, newDiet: Diets)
    fun changeBeliefs(reference:String, newBelief: Beliefs)
    fun changeSmokes(reference:String, newSmokes: Occurrences)
    fun changeDrinks(reference:String, newDrinks: Occurrences)
    fun changeSocialStatus(reference:String, newSocialStatus: SocialStatus)
    fun changeBiography(reference:String, newBiography:String)
    fun changeCity(reference:String, newCity:String)
    fun changeCountry(reference:String, newCountry:String)
}