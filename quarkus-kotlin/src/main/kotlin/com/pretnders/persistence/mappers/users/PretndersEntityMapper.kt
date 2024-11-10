package com.pretnders.persistence.mappers.users

import com.pretnders.domain.models.commands.users.CreatePretenderCommand
import com.pretnders.domain.models.users.Pretnder
import com.pretnders.persistence.entities.PretndersEntity
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
interface PretndersEntityMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pretenderDetails", ignore = true)
    @Mapping(target = "device", ignore = true)
    @Mapping(target = "passwordVerificationCode", ignore = true)
    @Mapping(target = "passwordVerificationTimestamp", ignore = true)
    @Mapping(target = "accountVerifiedStatus", ignore = true)
    fun fromCreatePretnderToEntity(createPretenderCommand: CreatePretenderCommand): PretndersEntity
    fun fromEntityToPretnder(pretndersEntity: PretndersEntity): Pretnder
    @Mapping(target = "device", ignore = true)
    @Mapping(target = "pretenderDetails", ignore = true)
    fun fromUserToEntity(pretnder: Pretnder): PretndersEntity
}