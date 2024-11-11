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
    fun findNextPicOrder(reference:String):Short {
        val res =  find("SELECT count(*) FROM ProfilePicsEntity pp WHERE pp.pretnders.id = (SELECT p.id FROM " +
                "PretndersEntity p " +
                "WHERE" +
                " p.reference = :reference LIMIT 1)" , mapOf(
                    "reference" to reference
                )
        ).project(Short::class.java).firstResult<Short>()
        if(res >= 9 || res < 0){
            throw ApplicationException(ApplicationExceptionsEnum.PICTURE_ORDER_OUT_OF_BOUND)
        }
        return res
    }
}
