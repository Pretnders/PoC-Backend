package pretnders.api.profile_pictures.persistence.mappers

import pretnders.api.profile_pictures.domain.models.ProfilePicture
import pretnders.api.profile_pictures.persistence.entities.ProfilePicsEntity
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
interface ProfilePicturesEntityMapper {
    fun fromEntityToModel(profilePicsEntity: ProfilePicsEntity): ProfilePicture
}