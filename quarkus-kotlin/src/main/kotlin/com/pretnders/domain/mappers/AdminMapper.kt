package com.pretnders.domain.mappers

import com.pretnders.domain.models.admins.AdminLoggedIn
import com.pretnders.domain.models.admins.Admins
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
interface AdminMapper {
    fun fromAdminToAdminLoggedIn(admins: Admins, jwToken:String):AdminLoggedIn
}