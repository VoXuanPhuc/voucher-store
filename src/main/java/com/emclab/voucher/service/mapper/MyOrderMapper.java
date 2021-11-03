package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.*;
import com.emclab.voucher.service.dto.MyOrderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MyOrder} and its DTO {@link MyOrderDTO}.
 */
@Mapper(componentModel = "spring", uses = { MyUserMapper.class, OrderStatusMapper.class })
public interface MyOrderMapper extends EntityMapper<MyOrderDTO, MyOrder> {
    @Mapping(target = "user", source = "user", qualifiedByName = "id")
    @Mapping(target = "status", source = "status", qualifiedByName = "id")
    MyOrderDTO toDto(MyOrder s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MyOrderDTO toDtoId(MyOrder myOrder);
}
