package com.emclab.voucher.service.impl;

import com.emclab.voucher.domain.FeedbackImage;
import com.emclab.voucher.repository.FeedbackImageRepository;
import com.emclab.voucher.service.FeedbackImageService;
import com.emclab.voucher.service.dto.FeedbackImageDTO;
import com.emclab.voucher.service.mapper.FeedbackImageMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FeedbackImage}.
 */
@Service
@Transactional
public class FeedbackImageServiceImpl implements FeedbackImageService {

    private final Logger log = LoggerFactory.getLogger(FeedbackImageServiceImpl.class);

    private final FeedbackImageRepository feedbackImageRepository;

    private final FeedbackImageMapper feedbackImageMapper;

    public FeedbackImageServiceImpl(FeedbackImageRepository feedbackImageRepository, FeedbackImageMapper feedbackImageMapper) {
        this.feedbackImageRepository = feedbackImageRepository;
        this.feedbackImageMapper = feedbackImageMapper;
    }

    @Override
    public FeedbackImageDTO save(FeedbackImageDTO feedbackImageDTO) {
        log.debug("Request to save FeedbackImage : {}", feedbackImageDTO);
        FeedbackImage feedbackImage = feedbackImageMapper.toEntity(feedbackImageDTO);
        feedbackImage = feedbackImageRepository.save(feedbackImage);
        return feedbackImageMapper.toDto(feedbackImage);
    }

    @Override
    public Optional<FeedbackImageDTO> partialUpdate(FeedbackImageDTO feedbackImageDTO) {
        log.debug("Request to partially update FeedbackImage : {}", feedbackImageDTO);

        return feedbackImageRepository
            .findById(feedbackImageDTO.getId())
            .map(
                existingFeedbackImage -> {
                    feedbackImageMapper.partialUpdate(existingFeedbackImage, feedbackImageDTO);

                    return existingFeedbackImage;
                }
            )
            .map(feedbackImageRepository::save)
            .map(feedbackImageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeedbackImageDTO> findAll() {
        log.debug("Request to get all FeedbackImages");
        return feedbackImageRepository.findAll().stream().map(feedbackImageMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FeedbackImageDTO> findOne(Long id) {
        log.debug("Request to get FeedbackImage : {}", id);
        return feedbackImageRepository.findById(id).map(feedbackImageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FeedbackImage : {}", id);
        feedbackImageRepository.deleteById(id);
    }
}
