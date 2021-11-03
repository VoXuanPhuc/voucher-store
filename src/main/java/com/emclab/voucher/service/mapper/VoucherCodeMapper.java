package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.*;
import com.emclab.voucher.service.dto.VoucherCodeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VoucherCode} and its DTO {@link VoucherCodeDTO}.
 */
@Mapper(componentModel = "spring", uses = { VoucherMapper.class, MyOrderMapper.class })
public interface VoucherCodeMapper extends EntityMapper<VoucherCodeDTO, VoucherCode> {
    @Mapping(target = "voucher", source = "voucher", qualifiedByName = "id")
    @Mapping(target = "order", source = "order", qualifiedByName = "id")
    VoucherCodeDTO toDto(VoucherCode s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VoucherCodeDTO toDtoId(VoucherCode voucherCode);
}
