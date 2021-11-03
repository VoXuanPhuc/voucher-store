package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.*;
import com.emclab.voucher.service.dto.VillageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Village} and its DTO {@link VillageDTO}.
 */
@Mapper(componentModel = "spring", uses = { DistrictMapper.class })
public interface VillageMapper extends EntityMapper<VillageDTO, Village> {
    @Mapping(target = "district", source = "district", qualifiedByName = "id")
    VillageDTO toDto(Village s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VillageDTO toDtoId(Village village);
}
