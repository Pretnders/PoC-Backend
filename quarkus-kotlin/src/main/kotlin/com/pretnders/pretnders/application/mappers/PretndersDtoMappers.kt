package com.pretnders.pretnders.application.mappers

import com.pretnders.pretnders.domain.models.CreatePretenderCommand
import com.pretnders.pretnders.domain.models.PretnderLoggedIn
import com.pretnders.pretnders.application.dto.requests.AddPretenderRequest
import com.pretnders.pretnders.application.dto.responses.LoginResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.CDI, uses = [PretnderDetailsDtoMappers::class])
interface PretndersDtoMappers {
    @Mapping(target = "reference", ignore = true)
    @Mapping(target = "verificationCode", ignore = true)
    @Mapping(target = "verificationCodeTimestamp", ignore = true)
    @Mapping(target = "accountVerified", ignore = true)
    fun fromCreationRequest(addPretenderRequest: AddPretenderRequest): CreatePretenderCommand
    fun toLoginResponse(user: PretnderLoggedIn): LoginResponse
}