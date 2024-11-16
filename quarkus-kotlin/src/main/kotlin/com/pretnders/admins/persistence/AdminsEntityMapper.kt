package com.pretnders.admins.persistence

import com.pretnders.admins.domain.Admin
import com.pretnders.admins.domain.CreateAdminCommand
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
interface AdminsEntityMapper {
    fun fomAdminCommandToEntity(createAdminCommand: CreateAdminCommand): AdminsEntity
    fun fromEntityToModel(adminsEntity: AdminsEntity): Admin
}