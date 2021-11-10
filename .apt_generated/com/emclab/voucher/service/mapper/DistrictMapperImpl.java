package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.District;
import com.emclab.voucher.service.dto.DistrictDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-10T10:27:46+0700",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.3.1300.v20210419-1022, environment: Java 14.0.2 (Oracle Corporation)"
)
@Component
public class DistrictMapperImpl implements DistrictMapper {

    @Autowired
    private ProvinceMapper provinceMapper;

    @Override
    public District toEntity(DistrictDTO dto) {
        if ( dto == null ) {
            return null;
        }

        District district = new District();

        district.id( dto.getId() );
        district.setName( dto.getName() );
        district.setProvince( provinceMapper.toEntity( dto.getProvince() ) );

        return district;
    }

    @Override
    public List<District> toEntity(List<DistrictDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<District> list = new ArrayList<District>( dtoList.size() );
        for ( DistrictDTO districtDTO : dtoList ) {
            list.add( toEntity( districtDTO ) );
        }

        return list;
    }

    @Override
    public List<DistrictDTO> toDto(List<District> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DistrictDTO> list = new ArrayList<DistrictDTO>( entityList.size() );
        for ( District district : entityList ) {
            list.add( toDto( district ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(District entity, DistrictDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getProvince() != null ) {
            entity.setProvince( provinceMapper.toEntity( dto.getProvince() ) );
        }
    }

    @Override
    public DistrictDTO toDto(District s) {
        if ( s == null ) {
            return null;
        }

        DistrictDTO districtDTO = new DistrictDTO();

        districtDTO.setProvince( provinceMapper.toDtoId( s.getProvince() ) );
        districtDTO.setId( s.getId() );
        districtDTO.setName( s.getName() );

        return districtDTO;
    }

    @Override
    public DistrictDTO toDtoId(District district) {
        if ( district == null ) {
            return null;
        }

        DistrictDTO districtDTO = new DistrictDTO();

        districtDTO.setId( district.getId() );

        return districtDTO;
    }
}
