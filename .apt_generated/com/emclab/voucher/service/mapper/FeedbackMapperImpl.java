package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.Feedback;
import com.emclab.voucher.service.dto.FeedbackDTO;
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
public class FeedbackMapperImpl implements FeedbackMapper {

    @Autowired
    private MyUserMapper myUserMapper;
    @Autowired
    private VoucherMapper voucherMapper;

    @Override
    public Feedback toEntity(FeedbackDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Feedback feedback = new Feedback();

        feedback.id( dto.getId() );
        feedback.setRate( dto.getRate() );
        feedback.setDetail( dto.getDetail() );
        feedback.setUser( myUserMapper.toEntity( dto.getUser() ) );
        feedback.setVoucher( voucherMapper.toEntity( dto.getVoucher() ) );

        return feedback;
    }

    @Override
    public List<Feedback> toEntity(List<FeedbackDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Feedback> list = new ArrayList<Feedback>( dtoList.size() );
        for ( FeedbackDTO feedbackDTO : dtoList ) {
            list.add( toEntity( feedbackDTO ) );
        }

        return list;
    }

    @Override
    public List<FeedbackDTO> toDto(List<Feedback> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<FeedbackDTO> list = new ArrayList<FeedbackDTO>( entityList.size() );
        for ( Feedback feedback : entityList ) {
            list.add( toDto( feedback ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Feedback entity, FeedbackDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getRate() != null ) {
            entity.setRate( dto.getRate() );
        }
        if ( dto.getDetail() != null ) {
            entity.setDetail( dto.getDetail() );
        }
        if ( dto.getUser() != null ) {
            entity.setUser( myUserMapper.toEntity( dto.getUser() ) );
        }
        if ( dto.getVoucher() != null ) {
            entity.setVoucher( voucherMapper.toEntity( dto.getVoucher() ) );
        }
    }

    @Override
    public FeedbackDTO toDto(Feedback s) {
        if ( s == null ) {
            return null;
        }

        FeedbackDTO feedbackDTO = new FeedbackDTO();

        feedbackDTO.setUser( myUserMapper.toDtoId( s.getUser() ) );
        feedbackDTO.setVoucher( voucherMapper.toDtoId( s.getVoucher() ) );
        feedbackDTO.setId( s.getId() );
        feedbackDTO.setRate( s.getRate() );
        feedbackDTO.setDetail( s.getDetail() );

        return feedbackDTO;
    }

    @Override
    public FeedbackDTO toDtoId(Feedback feedback) {
        if ( feedback == null ) {
            return null;
        }

        FeedbackDTO feedbackDTO = new FeedbackDTO();

        feedbackDTO.setId( feedback.getId() );

        return feedbackDTO;
    }
}
