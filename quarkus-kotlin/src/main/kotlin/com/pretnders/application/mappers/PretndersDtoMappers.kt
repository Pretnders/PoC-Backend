package com.pretnders.application.mappers

import com.pretnders.application.dto.requests.CreatePretenderRequest
import com.pretnders.application.dto.responses.CreateAccountResponse
import com.pretnders.application.dto.responses.PretnderLoginResponse
import com.pretnders.domain.models.pretnders.CreatePretenderCommand
import com.pretnders.domain.models.pretnders.UserBasicInformations
import com.pretnders.domain.models.pretnders.PretnderLoggedIn
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.CDI, uses = [PretnderDetailsDtoMappers::class])
interface PretndersDtoMappers {
    @Mapping(target = "reference", ignore = true)
    @Mapping(target = "verificationCode", ignore = true)
    @Mapping(target = "verificationCodeTimestamp", ignore = true)
    @Mapping(target = "accountVerified", ignore = true)
    fun fromCreationRequest(createPretenderRequest: CreatePretenderRequest): CreatePretenderCommand

    fun toCreationResponse(userBasicInformations: UserBasicInformations):CreateAccountResponse

    fun toLoginResponse(user: PretnderLoggedIn): PretnderLoginResponse
}