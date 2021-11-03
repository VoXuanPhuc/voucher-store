package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.*;
import com.emclab.voucher.service.dto.ServiceTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceType} and its DTO {@link ServiceTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServiceTypeMapper extends EntityMapper<ServiceTypeDTO, ServiceType> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ServiceTypeDTO toDtoId(ServiceType serviceType);
}
