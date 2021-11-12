package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.Gift;
import com.emclab.voucher.service.dto.GiftDTO;
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
public class GiftMapperImpl implements GiftMapper {

    @Autowired
    private MyUserMapper myUserMapper;
    @Autowired
    private VoucherCodeMapper voucherCodeMapper;

    @Override
    public Gift toEntity(GiftDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Gift gift = new Gift();

        gift.id( dto.getId() );
        gift.setMessage( dto.getMessage() );
        gift.setGiver( myUserMapper.toEntity( dto.getGiver() ) );
        gift.setVoucher( voucherCodeMapper.toEntity( dto.getVoucher() ) );

        return gift;
    }

    @Override
    public List<Gift> toEntity(List<GiftDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Gift> list = new ArrayList<Gift>( dtoList.size() );
        for ( GiftDTO giftDTO : dtoList ) {
            list.add( toEntity( giftDTO ) );
        }

        return list;
    }

    @Override
    public List<GiftDTO> toDto(List<Gift> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<GiftDTO> list = new ArrayList<GiftDTO>( entityList.size() );
        for ( Gift gift : entityList ) {
            list.add( toDto( gift ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Gift entity, GiftDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getMessage() != null ) {
            entity.setMessage( dto.getMessage() );
        }
        if ( dto.getGiver() != null ) {
            entity.setGiver( myUserMapper.toEntity( dto.getGiver() ) );
        }
        if ( dto.getVoucher() != null ) {
            entity.setVoucher( voucherCodeMapper.toEntity( dto.getVoucher() ) );
        }
    }

    @Override
    public GiftDTO toDto(Gift s) {
        if ( s == null ) {
            return null;
        }

        GiftDTO giftDTO = new GiftDTO();

        giftDTO.setGiver( myUserMapper.toDtoId( s.getGiver() ) );
        giftDTO.setVoucher( voucherCodeMapper.toDtoId( s.getVoucher() ) );
        giftDTO.setId( s.getId() );
        giftDTO.setMessage( s.getMessage() );

        return giftDTO;
    }
}
