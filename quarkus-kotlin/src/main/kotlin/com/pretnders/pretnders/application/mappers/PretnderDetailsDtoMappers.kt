package com.pretnders.pretnders.application.mappers

import com.pretnders.pretnders.application.dto.responses.FindDetailsResponse
import com.pretnders.pretnders.domain.models.PretnderDetails
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.CDI,)
interface PretnderDetailsDtoMappers {
    fun fromModelToResponse(pretnderDetails: PretnderDetails): FindDetailsResponse
}