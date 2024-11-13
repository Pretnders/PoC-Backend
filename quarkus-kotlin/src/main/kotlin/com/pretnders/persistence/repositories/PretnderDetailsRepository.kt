package com.pretnders.persistence.repositories

import com.pretnders.persistence.entities.PretnderDetailsEntity
import io.quarkus.hibernate.orm.panache.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class PretnderDetailsRepository: PanacheRepository<PretnderDetailsEntity?> {
    @Transactional
    fun updateOrientation(reference:String, orientation:String){
        update("SET orientation = :orientation WHERE reference = :reference",
            mapOf("orientation" to orientation,
                "reference" to reference))
    }
}