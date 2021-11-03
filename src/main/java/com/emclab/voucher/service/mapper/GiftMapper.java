package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.*;
import com.emclab.voucher.service.dto.GiftDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Gift} and its DTO {@link GiftDTO}.
 */
@Mapper(componentModel = "spring", uses = { MyUserMapper.class, VoucherCodeMapper.class })
public interface GiftMapper extends EntityMapper<GiftDTO, Gift> {
    @Mapping(target = "giver", source = "giver", qualifiedByName = "id")
    @Mapping(target = "voucher", source = "voucher", qualifiedByName = "id")
    GiftDTO toDto(Gift s);
}
