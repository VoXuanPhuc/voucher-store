package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.VoucherCode;
import com.emclab.voucher.service.dto.VoucherCodeDTO;
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
public class VoucherCodeMapperImpl implements VoucherCodeMapper {

    @Autowired
    private VoucherMapper voucherMapper;
    @Autowired
    private MyOrderMapper myOrderMapper;

    @Override
    public VoucherCode toEntity(VoucherCodeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        VoucherCode voucherCode = new VoucherCode();

        voucherCode.id( dto.getId() );
        voucherCode.setCode( dto.getCode() );
        voucherCode.setVoucher( voucherMapper.toEntity( dto.getVoucher() ) );
        voucherCode.setOrder( myOrderMapper.toEntity( dto.getOrder() ) );

        return voucherCode;
    }

    @Override
    public List<VoucherCode> toEntity(List<VoucherCodeDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<VoucherCode> list = new ArrayList<VoucherCode>( dtoList.size() );
        for ( VoucherCodeDTO voucherCodeDTO : dtoList ) {
            list.add( toEntity( voucherCodeDTO ) );
        }

        return list;
    }

    @Override
    public List<VoucherCodeDTO> toDto(List<VoucherCode> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<VoucherCodeDTO> list = new ArrayList<VoucherCodeDTO>( entityList.size() );
        for ( VoucherCode voucherCode : entityList ) {
            list.add( toDto( voucherCode ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(VoucherCode entity, VoucherCodeDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getCode() != null ) {
            entity.setCode( dto.getCode() );
        }
        if ( dto.getVoucher() != null ) {
            entity.setVoucher( voucherMapper.toEntity( dto.getVoucher() ) );
        }
        if ( dto.getOrder() != null ) {
            entity.setOrder( myOrderMapper.toEntity( dto.getOrder() ) );
        }
    }

    @Override
    public VoucherCodeDTO toDto(VoucherCode s) {
        if ( s == null ) {
            return null;
        }

        VoucherCodeDTO voucherCodeDTO = new VoucherCodeDTO();

        voucherCodeDTO.setVoucher( voucherMapper.toDtoId( s.getVoucher() ) );
        voucherCodeDTO.setOrder( myOrderMapper.toDtoId( s.getOrder() ) );
        voucherCodeDTO.setId( s.getId() );
        voucherCodeDTO.setCode( s.getCode() );

        return voucherCodeDTO;
    }

    @Override
    public VoucherCodeDTO toDtoId(VoucherCode voucherCode) {
        if ( voucherCode == null ) {
            return null;
        }

        VoucherCodeDTO voucherCodeDTO = new VoucherCodeDTO();

        voucherCodeDTO.setId( voucherCode.getId() );

        return voucherCodeDTO;
    }
}
