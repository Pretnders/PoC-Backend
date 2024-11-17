package pretnders.api.profile_pictures.persistence.repositories

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import pretnders.api.profile_pictures.persistence.entities.ProfilePicsEntity
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
@Transactional
class ProfilePicsCommandsRepository : PanacheRepository<ProfilePicsEntity> {

    fun swapPicturesOrder(
        swapperReference: String,
        swapperOrder: Long,
        swappedReference: String,
        swappedOrder: Long
    ) {
        update(
            "SET order = :swapperOrder WHERE reference = :swappedReference", mapOf(
                "swappedReference" to swappedReference,
                "swapperOrder" to swapperOrder,
            )
        )
        update(
            "SET order = :swappedOrder WHERE reference = :swapperReference", mapOf(
                "swapperReference" to swapperReference,
                "swappedOrder" to swappedOrder,
            )
        )
    }

    fun setPictureOrderById(newOrder:Short, pictureID:Long){
        update("SET order = :newPicOrder WHERE id = :picId", mapOf(
            "newPicOrder" to newOrder,
            "picId" to pictureID
        ))
    }

    fun changePictureUrl(pictureReference:String, newUrl:String){
        update("SET url = :newUrl WHERE reference = :pictureReference", mapOf(
            "pictureReference" to pictureReference,
            "newUrl" to newUrl
        ))
    }

}
