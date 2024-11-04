package com.pretnders.application.mappers

import com.pretnders.application.dto.requests.CreateAdminRequest
import com.pretnders.application.dto.requests.CreatePretenderRequest
import com.pretnders.application.dto.responses.CreatePretendersResponse
import com.pretnders.application.dto.responses.UserLoginResponse
import com.pretnders.domain.models.commands.users.CreatePretenderCommand
import com.pretnders.domain.models.users.UserBasicInformations
import com.pretnders.domain.models.users.UserLoggedIn
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
interface UsersDtoMappers {
    @Mapping(target = "reference", ignore = true)
    @Mapping(target = "verificationCode", ignore = true)
    @Mapping(target = "verificationCodeTimestamp", ignore = true)
    fun fromCreationRequest(createPretenderRequest: CreatePretenderRequest):CreatePretenderCommand

    @Mapping(target = "reference", ignore = true)
    @Mapping(target = "verificationCode", ignore = true)
    @Mapping(target = "verificationCodeTimestamp", ignore = true)
    fun fromCreationRequest(createAdminRequest: CreateAdminRequest):CreatePretenderCommand

    fun toCreationResponse(userBasicInformations: UserBasicInformations):CreatePretendersResponse
    fun toLoginResponse(user: UserLoggedIn): UserLoginResponse

}