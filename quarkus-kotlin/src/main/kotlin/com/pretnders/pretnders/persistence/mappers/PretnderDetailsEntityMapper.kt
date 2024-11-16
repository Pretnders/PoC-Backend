package com.pretnders.pretnders.persistence.mappers

import com.pretnders.pretnders.domain.models.PretnderDetails
import com.pretnders.pretnders.persistence.entities.PretnderDetailsEntity
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
interface PretnderDetailsEntityMapper {
    fun fromEntityToModel(pretnderDetailsEntity: PretnderDetailsEntity): PretnderDetails
    fun fromModelToEntity(pretenderDetails: PretnderDetails): PretnderDetailsEntity
}