package com.pretnders.domain.mappers

import com.pretnders.domain.models.pretnders.Pretnder
import com.pretnders.domain.models.pretnders.PretnderLoggedIn
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
interface PretndersMappers {
    fun fromUsersToUsersLoggedIn(pretnder: Pretnder, jwToken: String): PretnderLoggedIn
}