package pretnders.api.pretnders.persistence.mappers

import pretnders.api.pretnders.domain.models.PretnderDetails
import pretnders.api.pretnders.persistence.entities.PretnderDetailsEntity
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
interface PretnderDetailsEntityMapper {
    fun fromEntityToModel(pretnderDetailsEntity: PretnderDetailsEntity): PretnderDetails
}