package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.Province;
import com.emclab.voucher.service.dto.ProvinceDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-10T10:27:44+0700",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.3.1300.v20210419-1022, environment: Java 14.0.2 (Oracle Corporation)"
)
@Component
public class ProvinceMapperImpl implements ProvinceMapper {

    @Override
    public Province toEntity(ProvinceDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Province province = new Province();

        province.id( dto.getId() );
        province.setName( dto.getName() );

        return province;
    }

    @Override
    public ProvinceDTO toDto(Province entity) {
        if ( entity == null ) {
            return null;
        }

        ProvinceDTO provinceDTO = new ProvinceDTO();

        provinceDTO.setId( entity.getId() );
        provinceDTO.setName( entity.getName() );

        return provinceDTO;
    }

    @Override
    public List<Province> toEntity(List<ProvinceDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Province> list = new ArrayList<Province>( dtoList.size() );
        for ( ProvinceDTO provinceDTO : dtoList ) {
            list.add( toEntity( provinceDTO ) );
        }

        return list;
    }

    @Override
    public List<ProvinceDTO> toDto(List<Province> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ProvinceDTO> list = new ArrayList<ProvinceDTO>( entityList.size() );
        for ( Province province : entityList ) {
            list.add( toDto( province ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Province entity, ProvinceDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
    }

    @Override
    public ProvinceDTO toDtoId(Province province) {
        if ( province == null ) {
            return null;
        }

        ProvinceDTO provinceDTO = new ProvinceDTO();

        provinceDTO.setId( province.getId() );

        return provinceDTO;
    }
}
