package com.pretnders.domain.mappers

import com.pretnders.domain.models.users.User
import com.pretnders.domain.models.users.UserLoggedIn
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
interface UsersMappers {
    fun fromUsersToUsersLoggedIn(user: User, jwToken: String): UserLoggedIn
}