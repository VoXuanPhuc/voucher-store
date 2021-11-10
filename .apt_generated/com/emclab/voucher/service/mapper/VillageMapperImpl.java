package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.Village;
import com.emclab.voucher.service.dto.VillageDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-10T10:27:45+0700",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.3.1300.v20210419-1022, environment: Java 14.0.2 (Oracle Corporation)"
)
@Component
public class VillageMapperImpl implements VillageMapper {

    @Autowired
    private DistrictMapper districtMapper;

    @Override
    public Village toEntity(VillageDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Village village = new Village();

        village.id( dto.getId() );
        village.setName( dto.getName() );
        village.setDistrict( districtMapper.toEntity( dto.getDistrict() ) );

        return village;
    }

    @Override
    public List<Village> toEntity(List<VillageDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Village> list = new ArrayList<Village>( dtoList.size() );
        for ( VillageDTO villageDTO : dtoList ) {
            list.add( toEntity( villageDTO ) );
        }

        return list;
    }

    @Override
    public List<VillageDTO> toDto(List<Village> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<VillageDTO> list = new ArrayList<VillageDTO>( entityList.size() );
        for ( Village village : entityList ) {
            list.add( toDto( village ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Village entity, VillageDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getDistrict() != null ) {
            entity.setDistrict( districtMapper.toEntity( dto.getDistrict() ) );
        }
    }

    @Override
    public VillageDTO toDto(Village s) {
        if ( s == null ) {
            return null;
        }

        VillageDTO villageDTO = new VillageDTO();

        villageDTO.setDistrict( districtMapper.toDtoId( s.getDistrict() ) );
        villageDTO.setId( s.getId() );
        villageDTO.setName( s.getName() );

        return villageDTO;
    }

    @Override
    public VillageDTO toDtoId(Village village) {
        if ( village == null ) {
            return null;
        }

        VillageDTO villageDTO = new VillageDTO();

        villageDTO.setId( village.getId() );

        return villageDTO;
    }
}
