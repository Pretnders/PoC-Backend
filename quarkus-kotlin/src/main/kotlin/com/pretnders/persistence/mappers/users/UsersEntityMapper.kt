package com.pretnders.persistence.mappers.users

import com.pretnders.domain.models.commands.users.CreatePretenderCommand
import com.pretnders.domain.models.users.User
import com.pretnders.persistence.entities.PretendersEntity
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
interface UsersEntityMapper {
    fun fromCreateUserToEntity(createPretenderCommand: CreatePretenderCommand): PretendersEntity
    fun fromEntityToUser(pretendersEntity: PretendersEntity): User
    fun fromUserToEntity(user: User): PretendersEntity
}