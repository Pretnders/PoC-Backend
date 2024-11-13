package com.pretnders.domain.ports.out

interface UpdatePretnderProfileOut {
    fun changeHeight(reference:String, newHeight:String)
    fun changeBodyType(reference:String, newBodyType:String)
    fun changeDiet(reference:String, newDiet:String)
    fun changeBeliefs(reference:String, newBelief:String)
    fun changeSmokes(reference:String, newSmokes:String)
    fun changeDrinks(reference:String, newDrinks:String)
    fun changeSocialStatus(reference:String, newSocialStatus:String)
    fun changeBiography(reference:String, newBiography:String)
    fun changeCity(reference:String, newCity:String)
    fun changeCountry(reference:String, newCountry:String)
    fun changeNickname(reference:String, newNickname: String)
    fun changeOrientation(reference:String, newOrientation:String)
    fun changeGender(reference:String, newGender:String)
}