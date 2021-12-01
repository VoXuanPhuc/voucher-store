package com.emclab.voucher.service.impl;

import com.emclab.voucher.domain.Feedback;
import com.emclab.voucher.domain.Voucher;
import com.emclab.voucher.repository.FeedbackRepository;
import com.emclab.voucher.repository.VoucherRepository;
import com.emclab.voucher.service.FeedbackService;
import com.emclab.voucher.service.dto.FeedbackDTO;
import com.emclab.voucher.service.mapper.FeedbackMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Feedback}.
 */
@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    private final Logger log = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    private final FeedbackRepository feedbackRepository;

    private final FeedbackMapper feedbackMapper;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, FeedbackMapper feedbackMapper) {
        this.feedbackRepository = feedbackRepository;
        this.feedbackMapper = feedbackMapper;
    }

    @Autowired
    VoucherRepository voucherRepository;

    @Override
    public FeedbackDTO save(FeedbackDTO feedbackDTO) {
        log.debug("Request to save Feedback : {}", feedbackDTO);
        Feedback feedback = feedbackMapper.toEntity(feedbackDTO);
        feedback = feedbackRepository.save(feedback);
        return feedbackMapper.toDto(feedback);
    }

    @Override
    public Optional<FeedbackDTO> partialUpdate(FeedbackDTO feedbackDTO) {
        log.debug("Request to partially update Feedback : {}", feedbackDTO);

        return feedbackRepository
            .findById(feedbackDTO.getId())
            .map(
                existingFeedback -> {
                    feedbackMapper.partialUpdate(existingFeedback, feedbackDTO);

                    return existingFeedback;
                }
            )
            .map(feedbackRepository::save)
            .map(feedbackMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeedbackDTO> findAll() {
        log.debug("Request to get all Feedbacks");
        return feedbackRepository.findAll().stream().map(feedbackMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FeedbackDTO> findOne(Long id) {
        log.debug("Request to get Feedback : {}", id);
        return feedbackRepository.findById(id).map(feedbackMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Feedback : {}", id);
        feedbackRepository.deleteById(id);
    }

    @Override
    public List<FeedbackDTO> findByVoucher(long idVoucher, int page) {
        Voucher voucher = voucherRepository.findById(idVoucher).get();
        Pageable paging = PageRequest.of(page, 5);
        Page<Feedback> feedbackPage = feedbackRepository.findByVoucher(voucher, paging);
        List<FeedbackDTO> feedBackResult = feedbackMapper.toDto(feedbackPage.getContent());
        return feedBackResult;
    }

    @Override
    public Long countByVoucher(long idVoucher) {
        Voucher voucher = voucherRepository.findById(idVoucher).get();
        List<Feedback> feedbacks = feedbackRepository.findByVoucher(voucher);
        return (long) feedbacks.size();
    }
}
