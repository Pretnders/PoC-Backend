package com.pretnders.persistence.mappers.pretnders

import com.pretnders.domain.models.admins.Admins
import com.pretnders.domain.models.admins.CreateAdminCommand
import com.pretnders.persistence.entities.AdminsEntity
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
interface AdminsEntityMapper {
    fun fomAdminCommandToEntity(createAdminCommand: CreateAdminCommand): AdminsEntity
    fun fromEntityToModel(adminsEntity: AdminsEntity): Admins
}