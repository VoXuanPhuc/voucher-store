package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.FeedbackImage;
import com.emclab.voucher.service.dto.FeedbackImageDTO;
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
public class FeedbackImageMapperImpl implements FeedbackImageMapper {

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public FeedbackImage toEntity(FeedbackImageDTO dto) {
        if ( dto == null ) {
            return null;
        }

        FeedbackImage feedbackImage = new FeedbackImage();

        feedbackImage.id( dto.getId() );
        feedbackImage.setContent( dto.getContent() );
        feedbackImage.setFeedback( feedbackMapper.toEntity( dto.getFeedback() ) );

        return feedbackImage;
    }

    @Override
    public List<FeedbackImage> toEntity(List<FeedbackImageDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<FeedbackImage> list = new ArrayList<FeedbackImage>( dtoList.size() );
        for ( FeedbackImageDTO feedbackImageDTO : dtoList ) {
            list.add( toEntity( feedbackImageDTO ) );
        }

        return list;
    }

    @Override
    public List<FeedbackImageDTO> toDto(List<FeedbackImage> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<FeedbackImageDTO> list = new ArrayList<FeedbackImageDTO>( entityList.size() );
        for ( FeedbackImage feedbackImage : entityList ) {
            list.add( toDto( feedbackImage ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(FeedbackImage entity, FeedbackImageDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getContent() != null ) {
            entity.setContent( dto.getContent() );
        }
        if ( dto.getFeedback() != null ) {
            entity.setFeedback( feedbackMapper.toEntity( dto.getFeedback() ) );
        }
    }

    @Override
    public FeedbackImageDTO toDto(FeedbackImage s) {
        if ( s == null ) {
            return null;
        }

        FeedbackImageDTO feedbackImageDTO = new FeedbackImageDTO();

        feedbackImageDTO.setFeedback( feedbackMapper.toDtoId( s.getFeedback() ) );
        feedbackImageDTO.setId( s.getId() );
        feedbackImageDTO.setContent( s.getContent() );

        return feedbackImageDTO;
    }
}
