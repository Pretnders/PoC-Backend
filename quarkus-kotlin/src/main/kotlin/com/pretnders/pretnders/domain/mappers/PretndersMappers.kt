package com.pretnders.pretnders.domain.mappers

import com.pretnders.pretnders.domain.models.Pretnder
import com.pretnders.pretnders.domain.models.PretnderLoggedIn
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
interface PretndersMappers {
    fun fromUsersToUsersLoggedIn(pretnder: Pretnder, jwToken: String): PretnderLoggedIn
}