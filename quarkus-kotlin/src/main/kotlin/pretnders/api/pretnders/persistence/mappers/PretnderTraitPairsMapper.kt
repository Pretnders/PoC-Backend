package pretnders.api.pretnders.persistence.mappers

import pretnders.api.pretnders.domain.models.PretnderTraitPair
import pretnders.api.pretnders.persistence.entities.PretnderTraitPairsEntity
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
    fun fromEntityToDbModel(entity: PretnderTraitPairsEntity): PretnderTraitPair
}