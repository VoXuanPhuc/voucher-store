package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.*;
import com.emclab.voucher.service.dto.VoucherDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Voucher} and its DTO {@link VoucherDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMapper.class, EventMapper.class, ServiceTypeMapper.class, VoucherStatusMapper.class })
public interface VoucherMapper extends EntityMapper<VoucherDTO, Voucher> {
    @Mapping(target = "products", source = "products", qualifiedByName = "idSet")
    @Mapping(target = "event", source = "event", qualifiedByName = "id")
    @Mapping(target = "type", source = "type", qualifiedByName = "id")
    @Mapping(target = "status", source = "status", qualifiedByName = "id")
    VoucherDTO toDto(Voucher s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VoucherDTO toDtoId(Voucher voucher);

    @Mapping(target = "removeProduct", ignore = true)
    Voucher toEntity(VoucherDTO voucherDTO);
}
