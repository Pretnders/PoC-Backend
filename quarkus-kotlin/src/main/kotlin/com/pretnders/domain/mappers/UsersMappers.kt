package com.pretnders.domain.mappers

import com.pretnders.domain.models.users.Pretnder
import com.pretnders.domain.models.users.UserLoggedIn
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
interface UsersMappers {
    @Mapping(target = "type", ignore = true)

    fun fromUsersToUsersLoggedIn(pretnder: Pretnder, jwToken: String): UserLoggedIn
}