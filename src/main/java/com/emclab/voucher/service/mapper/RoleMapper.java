package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.*;
import com.emclab.voucher.service.dto.RoleDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Role} and its DTO {@link RoleDTO}.
 */
@Mapper(componentModel = "spring", uses = { MyUserMapper.class })
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {
    @Mapping(target = "users", source = "users", qualifiedByName = "idSet")
    RoleDTO toDto(Role s);

    @Mapping(target = "removeUser", ignore = true)
    Role toEntity(RoleDTO roleDTO);
}
