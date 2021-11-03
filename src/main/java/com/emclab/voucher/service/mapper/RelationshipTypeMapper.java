package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.*;
import com.emclab.voucher.service.dto.RelationshipTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RelationshipType} and its DTO {@link RelationshipTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RelationshipTypeMapper extends EntityMapper<RelationshipTypeDTO, RelationshipType> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RelationshipTypeDTO toDtoId(RelationshipType relationshipType);
}
