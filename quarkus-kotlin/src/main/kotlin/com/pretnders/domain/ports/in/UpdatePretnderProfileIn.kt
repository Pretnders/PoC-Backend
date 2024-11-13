package com.pretnders.domain.ports.`in`

import com.pretnders.domain.models.pretnders_details.BodyTypes
import com.pretnders.domain.models.pretnders_details.Genders
import com.pretnders.domain.models.pretnders_details.SexualOrientation

interface UpdatePretnderProfileIn {
    fun changeNickname(reference:String, nickname: String)
    fun changeOrientation(reference:String, orientation: SexualOrientation)
    fun changeGender(reference:String, gender: Genders)
    fun changeHeight(reference:String, newHeight:String)
    fun changeBodyType(reference:String, newBodyType:BodyTypes)
    fun changeDiet(reference:String, newDiet:String)
    fun changeBeliefs(reference:String, newBelief:String)
    fun changeSmokes(reference:String, newSmokes:String)
    fun changeDrinks(reference:String, newDrinks:String)
    fun changeSocialStatus(reference:String, newSocialStatus:String)
    fun changeBiography(reference:String, newBiography:String)
    fun changeCity(reference:String, newCity:String)
    fun changeCountry(reference:String, newCountry:String)
}