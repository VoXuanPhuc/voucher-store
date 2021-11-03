package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.*;
import com.emclab.voucher.service.dto.MyUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MyUser} and its DTO {@link MyUserDTO}.
 */
@Mapper(componentModel = "spring", uses = { AddressMapper.class, RoleMapper.class })
public interface MyUserMapper extends EntityMapper<MyUserDTO, MyUser> {
    @Mapping(target = "address", source = "address", qualifiedByName = "id")
    @Mapping(target = "role", source = "role", qualifiedByName = "id")
    MyUserDTO toDto(MyUser s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MyUserDTO toDtoId(MyUser myUser);
}
