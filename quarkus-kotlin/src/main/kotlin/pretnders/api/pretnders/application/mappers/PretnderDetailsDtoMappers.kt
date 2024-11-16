package pretnders.api.pretnders.application.mappers

import pretnders.api.pretnders.application.dto.responses.FindDetailsResponse
import pretnders.api.pretnders.domain.models.PretnderDetails
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.CDI,)
interface PretnderDetailsDtoMappers {
    fun fromModelToResponse(pretnderDetails: PretnderDetails): FindDetailsResponse
}