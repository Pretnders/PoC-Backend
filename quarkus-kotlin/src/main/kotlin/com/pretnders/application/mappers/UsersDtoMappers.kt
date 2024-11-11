package com.pretnders.application.mappers

import com.pretnders.application.dto.requests.CreatePretenderRequest
import com.pretnders.application.dto.responses.CreateAccountResponse
import com.pretnders.application.dto.responses.UserLoginResponse
import com.pretnders.domain.models.pretnders.CreatePretenderCommand
import com.pretnders.domain.models.pretnders.UserBasicInformations
import com.pretnders.domain.models.pretnders.UserLoggedIn
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
interface UsersDtoMappers {
    @Mapping(target = "reference", ignore = true)
    @Mapping(target = "verificationCode", ignore = true)
    @Mapping(target = "verificationCodeTimestamp", ignore = true)

    @Mapping(target = "accountVerified", ignore = true)

    fun fromCreationRequest(createPretenderRequest: CreatePretenderRequest): CreatePretenderCommand


    fun toCreationResponse(userBasicInformations: UserBasicInformations):CreateAccountResponse
    fun toLoginResponse(user: UserLoggedIn): UserLoginResponse

}