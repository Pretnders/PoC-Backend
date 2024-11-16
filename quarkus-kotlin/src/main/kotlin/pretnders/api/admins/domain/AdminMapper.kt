package pretnders.api.admins.domain

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
interface AdminMapper {
    fun fromAdminToAdminLoggedIn(admin: Admin, jwToken:String): AdminLoggedIn
}