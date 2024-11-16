package pretnders.api.profile_pictures.persistence.repositories

import pretnders.api.profile_pictures.persistence.entities.ProfilePicsEntity
import pretnders.api.profile_pictures.persistence.models.projections.ProfilePicNoPretnder
import pretnders.api.shared.errors.ApplicationException
import pretnders.api.shared.errors.ApplicationExceptionsEnum
import io.quarkus.hibernate.orm.panache.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.LockModeType

@ApplicationScoped
class FindProfilePicsRepository  : PanacheRepository<ProfilePicsEntity?> {
    fun findNextPicOrder(pretnderId:Long):Long {
        val res =  find("SELECT count(*) FROM ProfilePicsEntity pp WHERE pp.pretnder.id = :id" , mapOf(
            "id" to pretnderId
        )
        ).project(Long::class.java).firstResult<Long>()
        if(res >= 9 || res < 0){
            throw ApplicationException(ApplicationExceptionsEnum.PICTURE_ORDER_OUT_OF_BOUND)
        }
        return res
    }
    fun findPretnderPictureListByPictureReference(pictureReference:String):MutableList<ProfilePicNoPretnder> {
        return find(
            "SELECT id, order, reference FROM ProfilePicsEntity WHERE pretnder.id = (SELECT DISTINCT pretnder.id FROM" +
                    " ProfilePicsEntity WHERE reference = :pictureReference) ORDER BY order",
            mapOf(
                "pictureReference" to pictureReference
            )
        ).withLock<ProfilePicsEntity>(LockModeType.PESSIMISTIC_READ).project(ProfilePicNoPretnder::class.java).list()
    }
}