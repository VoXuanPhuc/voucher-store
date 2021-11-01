package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.*;
import com.emclab.voucher.service.dto.StoreDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Store} and its DTO {@link StoreDTO}.
 */
@Mapper(componentModel = "spring", uses = { AddressMapper.class, BenifitPackageMapper.class })
public interface StoreMapper extends EntityMapper<StoreDTO, Store> {
    @Mapping(target = "address", source = "address", qualifiedByName = "id")
    @Mapping(target = "pack", source = "pack", qualifiedByName = "id")
    StoreDTO toDto(Store s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StoreDTO toDtoId(Store store);
}
