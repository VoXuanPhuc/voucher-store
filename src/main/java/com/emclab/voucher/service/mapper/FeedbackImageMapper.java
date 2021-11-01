package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.*;
import com.emclab.voucher.service.dto.FeedbackImageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FeedbackImage} and its DTO {@link FeedbackImageDTO}.
 */
@Mapper(componentModel = "spring", uses = { FeedbackMapper.class })
public interface FeedbackImageMapper extends EntityMapper<FeedbackImageDTO, FeedbackImage> {
    @Mapping(target = "feedback", source = "feedback", qualifiedByName = "id")
    FeedbackImageDTO toDto(FeedbackImage s);
}
