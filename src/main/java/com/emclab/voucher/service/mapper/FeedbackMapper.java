package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.*;
import com.emclab.voucher.service.dto.FeedbackDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Feedback} and its DTO {@link FeedbackDTO}.
 */
@Mapper(componentModel = "spring", uses = { MyUserMapper.class, VoucherMapper.class })
public interface FeedbackMapper extends EntityMapper<FeedbackDTO, Feedback> {
    @Mapping(target = "user", source = "user", qualifiedByName = "id")
    @Mapping(target = "voucher", source = "voucher", qualifiedByName = "id")
    FeedbackDTO toDto(Feedback s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FeedbackDTO toDtoId(Feedback feedback);
}
