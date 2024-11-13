package com.pretnders.persistence.mappers.pretnders

import com.pretnders.domain.models.pretnders.PretnderTraitPair
import com.pretnders.persistence.entities.PretnderTraitPairsEntity
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
interface PretnderTraitPairsMapper {
    @Mapping(source="trait.trait",target="trait")
    @Mapping(source="trait.mirrorTrait",target="mirrorTrait")
    @Mapping(source="trait.description",target="description")
    @Mapping(source="score",target="score")
    @Mapping(source="reference",target="reference")
    fun fromEntityToDbModel(entity:PretnderTraitPairsEntity): PretnderTraitPair
    fun fromEntitiesToDbModels(entities:List<PretnderTraitPairsEntity>): List<PretnderTraitPair>
    @Mapping(source="trait",target="trait.trait")
    @Mapping(source="mirrorTrait",target="trait.mirrorTrait")
    @Mapping(source="description",target="trait.description")
    @Mapping(source="score",target="score")
    @Mapping(source="reference",target="reference")
    fun fromDbModelToEntity(model:PretnderTraitPair): PretnderTraitPairsEntity
}