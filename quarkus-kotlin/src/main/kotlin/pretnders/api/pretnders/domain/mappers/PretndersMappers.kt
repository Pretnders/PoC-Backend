package pretnders.api.pretnders.domain.mappers

import pretnders.api.pretnders.domain.models.Pretnder
import pretnders.api.pretnders.domain.models.PretnderLoggedIn
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
interface PretndersMappers {
    fun fromUsersToUsersLoggedIn(pretnder: Pretnder, jwToken: String): PretnderLoggedIn
}