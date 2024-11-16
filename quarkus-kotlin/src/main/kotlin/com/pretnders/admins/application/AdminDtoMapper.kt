package com.pretnders.admins.application

import com.pretnders.admins.domain.AdminLoggedIn
import com.pretnders.admins.domain.CreateAdminCommand
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)

interface AdminDtoMapper {
    fun toAdminResponse(adminLoggedIn: AdminLoggedIn): AdminLoginResponse

    @Mapping(target = "reference", ignore = true)
    fun fromCreationRequest(createAdminRequest: CreateAdminRequest): CreateAdminCommand
}