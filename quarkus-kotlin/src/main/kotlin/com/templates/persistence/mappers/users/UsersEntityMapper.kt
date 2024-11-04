package com.templates.persistence.mappers.users

import com.templates.domain.models.commands.users.CreateUserCommand
import com.templates.domain.models.users.User
import com.templates.persistence.entities.AdminsEntity
import com.templates.persistence.entities.ClientsEntity
import com.templates.persistence.entities.UsersEntity
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
interface UsersEntityMapper {
    fun fromCreateUserToEntity(createUserCommand: CreateUserCommand): UsersEntity
    fun fromCreateUserToClient(createUserCommand: CreateUserCommand): ClientsEntity
    fun fromCreateUserToAdmin(createUserCommand: CreateUserCommand): AdminsEntity

    fun fromEntityToUser(usersEntity: UsersEntity): User
    fun fromClientToUser(clientsEntity: ClientsEntity): User

    fun fromAdminToUser(adminsEntity: AdminsEntity): User
    fun fromUserToEntity(user: User): UsersEntity

}