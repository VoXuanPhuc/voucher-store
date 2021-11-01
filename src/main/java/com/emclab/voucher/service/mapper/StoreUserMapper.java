package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.*;
import com.emclab.voucher.service.dto.StoreUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link StoreUser} and its DTO {@link StoreUserDTO}.
 */
@Mapper(componentModel = "spring", uses = { RelationshipTypeMapper.class, MyUserMapper.class, StoreMapper.class })
public interface StoreUserMapper extends EntityMapper<StoreUserDTO, StoreUser> {
    @Mapping(target = "type", source = "type", qualifiedByName = "id")
    @Mapping(target = "user", source = "user", qualifiedByName = "id")
    @Mapping(target = "store", source = "store", qualifiedByName = "id")
    StoreUserDTO toDto(StoreUser s);
}
