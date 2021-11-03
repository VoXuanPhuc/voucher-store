package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.*;
import com.emclab.voucher.service.dto.DistrictDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link District} and its DTO {@link DistrictDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProvinceMapper.class })
public interface DistrictMapper extends EntityMapper<DistrictDTO, District> {
    @Mapping(target = "province", source = "province", qualifiedByName = "id")
    DistrictDTO toDto(District s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DistrictDTO toDtoId(District district);
}
