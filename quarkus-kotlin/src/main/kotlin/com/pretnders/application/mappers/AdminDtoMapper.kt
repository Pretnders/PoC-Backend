package com.pretnders.application.mappers

import com.pretnders.application.dto.requests.CreateAdminRequest
import com.pretnders.application.dto.responses.AdminLoginResponse
import com.pretnders.application.dto.responses.CreateAccountResponse
import com.pretnders.domain.models.admins.AdminLoggedIn
import com.pretnders.domain.models.admins.CreateAdminCommand
import com.pretnders.domain.models.pretnders.UserBasicInformations
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)

interface AdminDtoMapper {
    fun toAdminResponse(adminLoggedIn: AdminLoggedIn):AdminLoginResponse
    fun toCreationResponse(userBasicInformations: UserBasicInformations): CreateAccountResponse

    @Mapping(target = "reference", ignore = true)
    fun fromCreationRequest(createAdminRequest: CreateAdminRequest): CreateAdminCommand
}