package pretnders.api.admins.persistence

import pretnders.api.admins.domain.Admin
import pretnders.api.admins.domain.CreateAdminCommand
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
interface AdminsEntityMapper {
    fun fomAdminCommandToEntity(createAdminCommand: CreateAdminCommand): AdminsEntity
    fun fromEntityToModel(adminsEntity: AdminsEntity): Admin
}