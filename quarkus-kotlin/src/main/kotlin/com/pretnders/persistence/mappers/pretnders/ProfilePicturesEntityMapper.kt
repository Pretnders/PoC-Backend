package com.pretnders.persistence.mappers.pretnders

import com.pretnders.domain.models.profile_pics.ProfilePicture
import com.pretnders.persistence.entities.ProfilePicsEntity
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
interface ProfilePicturesEntityMapper {
    fun fromEntityToModel(profilePicsEntity: ProfilePicsEntity):ProfilePicture
}