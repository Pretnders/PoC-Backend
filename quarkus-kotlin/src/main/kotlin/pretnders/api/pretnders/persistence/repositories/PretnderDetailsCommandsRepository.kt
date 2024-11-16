package pretnders.api.pretnders.persistence.repositories

import pretnders.api.pretnders.persistence.entities.PretnderDetailsEntity
import io.quarkus.hibernate.orm.panache.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
@Transactional
class PretnderDetailsCommandsRepository: PanacheRepository<PretnderDetailsEntity?> {

    fun changeHeight(reference:String, height:String){
        update("SET height = :height WHERE reference = :reference",
            mapOf("height" to height,
                "reference" to reference))
    }

    fun changeBodyType(reference:String, bodyType:String){
        update("SET bodyType = :bodyType WHERE reference = :reference",
            mapOf("bodyType" to bodyType,
                "reference" to reference))
    }

    fun changeDiet(reference:String, diet:String){
        update("SET diet = :diet WHERE reference = :reference",
            mapOf("diet" to diet,
                "reference" to reference))
    }

    fun changeBeliefs(reference:String, beliefs:String){
        update("SET beliefs = :beliefs WHERE reference = :reference",
            mapOf("beliefs" to beliefs,
                "reference" to reference))
    }

    fun changeSmokes(reference:String, smokes:String){
        update("SET smokes = :smokes WHERE reference = :reference",
            mapOf("smokes" to smokes,
                "reference" to reference))
    }

    fun changeDrinks(reference:String, drinks:String){
        update("SET drinks = :drinks WHERE reference = :reference",
            mapOf("drinks" to drinks,
                "reference" to reference))
    }

    fun changeSocialStatus(reference:String, socialStatus:String){
        update("SET socialStatus = :socialStatus WHERE reference = :reference",
            mapOf("socialStatus" to socialStatus,
                "reference" to reference))
    }
    fun changeBiography(reference:String, biography:String){
        update("SET biography = :biography WHERE reference = :reference",
            mapOf("biography" to biography,
                "reference" to reference))
    }

    fun changeCity(reference:String, city:String){
        update("SET city = :city WHERE reference = :reference",
            mapOf("city" to city,
                "reference" to reference))
    }
    fun changeCountry(reference:String, country:String){
        update("SET country = :country WHERE reference = :reference",
            mapOf("country" to country,
                "reference" to reference))
    }

    fun changeGender(reference:String, gender:String){
        update("SET gender = :gender WHERE reference = :reference",
            mapOf("gender" to gender,
                "reference" to reference))
    }

    fun changeOrientation(reference:String, orientation:String){
        update("SET orientation = :orientation WHERE reference = :reference",
            mapOf("orientation" to orientation,
                "reference" to reference))
    }
}