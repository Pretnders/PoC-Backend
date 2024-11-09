package com.pretnders.persistence.mappers.users

import com.pretnders.domain.models.commands.users.CreateAdminCommand
import com.pretnders.persistence.entities.AdminsEntity
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
interface AdminsEntityMapper {
    fun fomAdminCommandToEntity(createAdminCommand: CreateAdminCommand): AdminsEntity

}