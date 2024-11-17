package pretnders.api.admins.persistence

import pretnders.api.admins.domain.Admin
import pretnders.api.admins.domain.CreateAdminCommand
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
interface AdminsEntityMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profilePictureUrl", ignore = true)
    fun fomAdminCommandToEntity(createAdminCommand: CreateAdminCommand): AdminsEntity
    fun fromEntityToModel(adminsEntity: AdminsEntity): Admin
}