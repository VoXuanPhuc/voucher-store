package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.BenifitPackage;
import com.emclab.voucher.service.dto.BenifitPackageDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-10T10:27:43+0700",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.3.1300.v20210419-1022, environment: Java 14.0.2 (Oracle Corporation)"
)
@Component
public class BenifitPackageMapperImpl implements BenifitPackageMapper {

    @Override
    public BenifitPackage toEntity(BenifitPackageDTO dto) {
        if ( dto == null ) {
            return null;
        }

        BenifitPackage benifitPackage = new BenifitPackage();

        benifitPackage.id( dto.getId() );
        benifitPackage.setName( dto.getName() );
        benifitPackage.setDescription( dto.getDescription() );
        benifitPackage.setCost( dto.getCost() );
        benifitPackage.setTime( dto.getTime() );

        return benifitPackage;
    }

    @Override
    public BenifitPackageDTO toDto(BenifitPackage entity) {
        if ( entity == null ) {
            return null;
        }

        BenifitPackageDTO benifitPackageDTO = new BenifitPackageDTO();

        benifitPackageDTO.setId( entity.getId() );
        benifitPackageDTO.setName( entity.getName() );
        benifitPackageDTO.setDescription( entity.getDescription() );
        benifitPackageDTO.setCost( entity.getCost() );
        benifitPackageDTO.setTime( entity.getTime() );

        return benifitPackageDTO;
    }

    @Override
    public List<BenifitPackage> toEntity(List<BenifitPackageDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<BenifitPackage> list = new ArrayList<BenifitPackage>( dtoList.size() );
        for ( BenifitPackageDTO benifitPackageDTO : dtoList ) {
            list.add( toEntity( benifitPackageDTO ) );
        }

        return list;
    }

    @Override
    public List<BenifitPackageDTO> toDto(List<BenifitPackage> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<BenifitPackageDTO> list = new ArrayList<BenifitPackageDTO>( entityList.size() );
        for ( BenifitPackage benifitPackage : entityList ) {
            list.add( toDto( benifitPackage ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(BenifitPackage entity, BenifitPackageDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getCost() != null ) {
            entity.setCost( dto.getCost() );
        }
        if ( dto.getTime() != null ) {
            entity.setTime( dto.getTime() );
        }
    }

    @Override
    public BenifitPackageDTO toDtoId(BenifitPackage benifitPackage) {
        if ( benifitPackage == null ) {
            return null;
        }

        BenifitPackageDTO benifitPackageDTO = new BenifitPackageDTO();

        benifitPackageDTO.setId( benifitPackage.getId() );

        return benifitPackageDTO;
    }
}
