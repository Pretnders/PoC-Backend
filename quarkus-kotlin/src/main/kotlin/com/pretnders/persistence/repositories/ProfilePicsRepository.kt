package com.pretnders.persistence.repositories
import com.pretnders.domain.errors.ApplicationException
import com.pretnders.domain.errors.ApplicationExceptionsEnum
import com.pretnders.persistence.entities.ProfilePicsEntity
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
