package com.pretnders.application.mappers

import com.pretnders.application.dto.responses.PretnderDetailsResponse
import com.pretnders.domain.models.pretnders.PretnderDetails
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.CDI,)
interface PretnderDetailsDtoMappers {
    fun fromModelToResponse(pretnderDetails: PretnderDetails):PretnderDetailsResponse
}