package com.pretnders.persistence.mappers.pretnders

import com.pretnders.domain.models.pretnders.PretnderDetails
import com.pretnders.persistence.entities.PretnderDetailsEntity
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
interface PretnderDetailsEntityMapper {
    fun fromEntityToModel(pretnderDetailsEntity: PretnderDetailsEntity): PretnderDetails
    fun fromModelToEntity(pretenderDetails: PretnderDetails): PretnderDetailsEntity
}