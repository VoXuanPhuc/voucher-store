package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.*;
import com.emclab.voucher.service.dto.VoucherStatusDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VoucherStatus} and its DTO {@link VoucherStatusDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VoucherStatusMapper extends EntityMapper<VoucherStatusDTO, VoucherStatus> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VoucherStatusDTO toDtoId(VoucherStatus voucherStatus);
}
