package com.pretnders.pretnders.persistence.repositories

import com.pretnders.pretnders.persistence.entities.PretnderDetailsEntity
import io.quarkus.hibernate.orm.panache.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class PretnderDetailsRepository: PanacheRepository<PretnderDetailsEntity?> {

    @Transactional
    fun updateHeight(reference:String, height:String){
        update("SET height = :height WHERE reference = :reference",
            mapOf("height" to height,
                "reference" to reference))
    }

    @Transactional
    fun updateBodyType(reference:String, bodyType:String){
        update("SET bodyType = :bodyType WHERE reference = :reference",
            mapOf("bodyType" to bodyType,
                "reference" to reference))
    }

    @Transactional
    fun updateDiet(reference:String, diet:String){
        update("SET diet = :diet WHERE reference = :reference",
            mapOf("diet" to diet,
                "reference" to reference))
    }

    @Transactional
    fun updateBeliefs(reference:String, beliefs:String){
        update("SET beliefs = :beliefs WHERE reference = :reference",
            mapOf("beliefs" to beliefs,
                "reference" to reference))
    }

    @Transactional
    fun updateSmokes(reference:String, smokes:String){
        update("SET smokes = :smokes WHERE reference = :reference",
            mapOf("smokes" to smokes,
                "reference" to reference))
    }

    @Transactional
    fun updateDrinks(reference:String, drinks:String){
        update("SET drinks = :drinks WHERE reference = :reference",
            mapOf("drinks" to drinks,
                "reference" to reference))
    }

    @Transactional
    fun updateSocialStatus(reference:String, socialStatus:String){
        update("SET socialStatus = :socialStatus WHERE reference = :reference",
            mapOf("socialStatus" to socialStatus,
                "reference" to reference))
    }
    @Transactional
    fun updateBiography(reference:String, biography:String){
        update("SET biography = :biography WHERE reference = :reference",
            mapOf("biography" to biography,
                "reference" to reference))
    }

    @Transactional
    fun updateCity(reference:String, city:String){
        update("SET city = :city WHERE reference = :reference",
            mapOf("city" to city,
                "reference" to reference))
    }
    @Transactional
    fun updateCountry(reference:String, country:String){
        update("SET country = :country WHERE reference = :reference",
            mapOf("country" to country,
                "reference" to reference))
    }

    @Transactional
    fun updateGender(reference:String, gender:String){
        update("SET gender = :gender WHERE reference = :reference",
            mapOf("gender" to gender,
                "reference" to reference))
    }

    @Transactional
    fun updateOrientation(reference:String, orientation:String){
        update("SET orientation = :orientation WHERE reference = :reference",
            mapOf("orientation" to orientation,
                "reference" to reference))
    }
}