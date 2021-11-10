package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.VoucherImage;
import com.emclab.voucher.service.dto.VoucherImageDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-10T10:27:44+0700",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.3.1300.v20210419-1022, environment: Java 14.0.2 (Oracle Corporation)"
)
@Component
public class VoucherImageMapperImpl implements VoucherImageMapper {

    @Autowired
    private VoucherMapper voucherMapper;

    @Override
    public VoucherImage toEntity(VoucherImageDTO dto) {
        if ( dto == null ) {
            return null;
        }

        VoucherImage voucherImage = new VoucherImage();

        voucherImage.id( dto.getId() );
        voucherImage.setName( dto.getName() );
        voucherImage.setVoucher( voucherMapper.toEntity( dto.getVoucher() ) );

        return voucherImage;
    }

    @Override
    public List<VoucherImage> toEntity(List<VoucherImageDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<VoucherImage> list = new ArrayList<VoucherImage>( dtoList.size() );
        for ( VoucherImageDTO voucherImageDTO : dtoList ) {
            list.add( toEntity( voucherImageDTO ) );
        }

        return list;
    }

    @Override
    public List<VoucherImageDTO> toDto(List<VoucherImage> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<VoucherImageDTO> list = new ArrayList<VoucherImageDTO>( entityList.size() );
        for ( VoucherImage voucherImage : entityList ) {
            list.add( toDto( voucherImage ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(VoucherImage entity, VoucherImageDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getVoucher() != null ) {
            entity.setVoucher( voucherMapper.toEntity( dto.getVoucher() ) );
        }
    }

    @Override
    public VoucherImageDTO toDto(VoucherImage s) {
        if ( s == null ) {
            return null;
        }

        VoucherImageDTO voucherImageDTO = new VoucherImageDTO();

        voucherImageDTO.setVoucher( voucherMapper.toDtoId( s.getVoucher() ) );
        voucherImageDTO.setId( s.getId() );
        voucherImageDTO.setName( s.getName() );

        return voucherImageDTO;
    }
}
