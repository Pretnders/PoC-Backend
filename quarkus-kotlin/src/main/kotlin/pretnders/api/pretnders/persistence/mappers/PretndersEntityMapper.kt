package pretnders.api.pretnders.persistence.mappers

import pretnders.api.pretnders.domain.models.CreatePretenderCommand
import pretnders.api.pretnders.domain.models.Pretnder
import pretnders.api.pretnders.persistence.entities.PretndersEntity
import pretnders.api.profile_pictures.persistence.mappers.ProfilePicturesEntityMapper
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA,
    uses = [ProfilePicturesEntityMapper::class, PretnderDetailsEntityMapper::class, PretnderTraitPairsMapper::class]
)
interface PretndersEntityMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "device", ignore = true)
    @Mapping(target = "passwordVerificationCode", ignore = true)
    @Mapping(target = "passwordVerificationTimestamp", ignore = true)
    @Mapping(target = "accountVerifiedStatus", ignore = true)
    @Mapping(target = "pretnderDetails", ignore = true)
    @Mapping(target = "profilePictures", ignore = true)
    @Mapping(target = "traitPairs", ignore = true)
    fun fromCreateCommandToEntity(createPretenderCommand: CreatePretenderCommand): PretndersEntity

    fun fromEntityToModel(pretndersEntity: PretndersEntity): Pretnder
}