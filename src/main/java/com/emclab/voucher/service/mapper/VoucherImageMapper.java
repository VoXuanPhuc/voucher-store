package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.*;
import com.emclab.voucher.service.dto.VoucherImageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VoucherImage} and its DTO {@link VoucherImageDTO}.
 */
@Mapper(componentModel = "spring", uses = { VoucherMapper.class })
public interface VoucherImageMapper extends EntityMapper<VoucherImageDTO, VoucherImage> {
    @Mapping(target = "voucher", source = "voucher", qualifiedByName = "id")
    VoucherImageDTO toDto(VoucherImage s);
}
