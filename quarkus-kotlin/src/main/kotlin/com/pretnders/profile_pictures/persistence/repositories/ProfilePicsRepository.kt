package com.pretnders.profile_pictures.persistence.repositories
import com.pretnders.profile_pictures.persistence.entities.ProfilePicsEntity
import com.pretnders.shared.errors.ApplicationException
import com.pretnders.shared.errors.ApplicationExceptionsEnum
import io.quarkus.hibernate.orm.panache.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
@Transactional
class ProfilePicsRepository : PanacheRepository<ProfilePicsEntity?> {
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
}
