package com.pretnders.persistence.mappers.pretnders

import com.pretnders.domain.models.pretnders.CreatePretenderCommand
import com.pretnders.domain.models.pretnders.Pretnder
import com.pretnders.persistence.entities.PretndersEntity
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA,
    uses = [ProfilePicturesEntityMapper::class, PretnderDetailsEntityMapper::class,PretnderTraitPairsMapper::class]
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