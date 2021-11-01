package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.*;
import com.emclab.voucher.service.dto.BenifitPackageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BenifitPackage} and its DTO {@link BenifitPackageDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BenifitPackageMapper extends EntityMapper<BenifitPackageDTO, BenifitPackage> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BenifitPackageDTO toDtoId(BenifitPackage benifitPackage);
}
