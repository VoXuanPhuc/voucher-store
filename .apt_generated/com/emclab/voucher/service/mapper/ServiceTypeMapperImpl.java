package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.ServiceType;
import com.emclab.voucher.service.dto.ServiceTypeDTO;
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
public class ServiceTypeMapperImpl implements ServiceTypeMapper {

    @Override
    public ServiceType toEntity(ServiceTypeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ServiceType serviceType = new ServiceType();

        serviceType.id( dto.getId() );
        serviceType.setName( dto.getName() );

        return serviceType;
    }

    @Override
    public ServiceTypeDTO toDto(ServiceType entity) {
        if ( entity == null ) {
            return null;
        }

        ServiceTypeDTO serviceTypeDTO = new ServiceTypeDTO();

        serviceTypeDTO.setId( entity.getId() );
        serviceTypeDTO.setName( entity.getName() );

        return serviceTypeDTO;
    }

    @Override
    public List<ServiceType> toEntity(List<ServiceTypeDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<ServiceType> list = new ArrayList<ServiceType>( dtoList.size() );
        for ( ServiceTypeDTO serviceTypeDTO : dtoList ) {
            list.add( toEntity( serviceTypeDTO ) );
        }

        return list;
    }

    @Override
    public List<ServiceTypeDTO> toDto(List<ServiceType> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ServiceTypeDTO> list = new ArrayList<ServiceTypeDTO>( entityList.size() );
        for ( ServiceType serviceType : entityList ) {
            list.add( toDto( serviceType ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(ServiceType entity, ServiceTypeDTO dto) {
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
    public ServiceTypeDTO toDtoId(ServiceType serviceType) {
        if ( serviceType == null ) {
            return null;
        }

        ServiceTypeDTO serviceTypeDTO = new ServiceTypeDTO();

        serviceTypeDTO.setId( serviceType.getId() );

        return serviceTypeDTO;
    }
}
