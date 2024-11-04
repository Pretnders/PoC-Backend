package com.templates.application.mappers

import com.templates.application.dto.requests.CreateAdminRequest
import com.templates.application.dto.requests.CreateUserRequest
import com.templates.application.dto.responses.CreateUserResponse
import com.templates.application.dto.responses.UserLoginResponse
import com.templates.domain.models.commands.users.CreateUserCommand
import com.templates.domain.models.users.UserBasicInformations
import com.templates.domain.models.users.UserLoggedIn
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
interface UsersDtoMappers {
    @Mapping(target = "reference", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "verificationCode", ignore = true)
    @Mapping(target = "verificationCodeTimestamp", ignore = true)
    fun fromCreationRequest(createUserRequest: CreateUserRequest):CreateUserCommand

    @Mapping(target = "reference", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "verificationCode", ignore = true)
    @Mapping(target = "verificationCodeTimestamp", ignore = true)
    fun fromCreationRequest(createAdminRequest: CreateAdminRequest):CreateUserCommand

    fun toCreationResponse(userBasicInformations: UserBasicInformations):CreateUserResponse
    fun toLoginResponse(user: UserLoggedIn): UserLoginResponse

}