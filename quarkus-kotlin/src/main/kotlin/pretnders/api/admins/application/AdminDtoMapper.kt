package pretnders.api.admins.application

import pretnders.api.admins.domain.AdminLoggedIn
import pretnders.api.admins.domain.CreateAdminCommand
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)

interface AdminDtoMapper {
    fun toAdminResponse(adminLoggedIn: AdminLoggedIn): AdminLoginResponse

    @Mapping(target = "reference", ignore = true)
    fun fromCreationRequest(createAdminRequest: CreateAdminRequest): CreateAdminCommand
}