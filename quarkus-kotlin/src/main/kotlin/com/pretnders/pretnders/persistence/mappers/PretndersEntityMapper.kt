package com.pretnders.pretnders.persistence.mappers

import com.pretnders.pretnders.domain.models.CreatePretenderCommand
import com.pretnders.pretnders.domain.models.Pretnder
import com.pretnders.pretnders.persistence.entities.PretndersEntity
import com.pretnders.profile_pictures.persistence.mappers.ProfilePicturesEntityMapper
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
    fun fromCreateCommandToEntity(createPretenderCommand: CreatePretenderCommand): PretndersEntity

    fun fromEntityToModel(pretndersEntity: PretndersEntity): Pretnder

    @Mapping(target = "pretnderDetails", ignore = true)
    fun fromPretnderToEntity(pretnder: Pretnder): PretndersEntity

    fun fromPretnderToEntityWithDetails(pretnder: Pretnder): PretndersEntity
}