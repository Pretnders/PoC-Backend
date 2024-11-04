package com.templates.domain.mappers

import com.templates.domain.models.users.User
import com.templates.domain.models.users.UserLoggedIn
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
interface UsersMappers {
    fun fromUsersToUsersLoggedIn(user: User, jwToken: String): UserLoggedIn
}