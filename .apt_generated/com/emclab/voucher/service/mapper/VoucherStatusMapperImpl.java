package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.VoucherStatus;
import com.emclab.voucher.service.dto.VoucherStatusDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-10T10:27:46+0700",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.3.1300.v20210419-1022, environment: Java 14.0.2 (Oracle Corporation)"
)
@Component
public class VoucherStatusMapperImpl implements VoucherStatusMapper {

    @Override
    public VoucherStatus toEntity(VoucherStatusDTO dto) {
        if ( dto == null ) {
            return null;
        }

        VoucherStatus voucherStatus = new VoucherStatus();

        voucherStatus.id( dto.getId() );
        voucherStatus.setName( dto.getName() );

        return voucherStatus;
    }

    @Override
    public VoucherStatusDTO toDto(VoucherStatus entity) {
        if ( entity == null ) {
            return null;
        }

        VoucherStatusDTO voucherStatusDTO = new VoucherStatusDTO();

        voucherStatusDTO.setId( entity.getId() );
        voucherStatusDTO.setName( entity.getName() );

        return voucherStatusDTO;
    }

    @Override
    public List<VoucherStatus> toEntity(List<VoucherStatusDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<VoucherStatus> list = new ArrayList<VoucherStatus>( dtoList.size() );
        for ( VoucherStatusDTO voucherStatusDTO : dtoList ) {
            list.add( toEntity( voucherStatusDTO ) );
        }

        return list;
    }

    @Override
    public List<VoucherStatusDTO> toDto(List<VoucherStatus> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<VoucherStatusDTO> list = new ArrayList<VoucherStatusDTO>( entityList.size() );
        for ( VoucherStatus voucherStatus : entityList ) {
            list.add( toDto( voucherStatus ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(VoucherStatus entity, VoucherStatusDTO dto) {
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
    public VoucherStatusDTO toDtoId(VoucherStatus voucherStatus) {
        if ( voucherStatus == null ) {
            return null;
        }

        VoucherStatusDTO voucherStatusDTO = new VoucherStatusDTO();

        voucherStatusDTO.setId( voucherStatus.getId() );

        return voucherStatusDTO;
    }
}
