package com.emclab.voucher.service.impl;

import com.emclab.voucher.domain.Gift;
import com.emclab.voucher.repository.GiftRepository;
import com.emclab.voucher.service.GiftService;
import com.emclab.voucher.service.dto.GiftDTO;
import com.emclab.voucher.service.mapper.GiftMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Gift}.
 */
@Service
@Transactional
public class GiftServiceImpl implements GiftService {

    private final Logger log = LoggerFactory.getLogger(GiftServiceImpl.class);

    private final GiftRepository giftRepository;

    private final GiftMapper giftMapper;

    public GiftServiceImpl(GiftRepository giftRepository, GiftMapper giftMapper) {
        this.giftRepository = giftRepository;
        this.giftMapper = giftMapper;
    }

    @Override
    public GiftDTO save(GiftDTO giftDTO) {
        log.debug("Request to save Gift : {}", giftDTO);
        Gift gift = giftMapper.toEntity(giftDTO);
        gift = giftRepository.save(gift);
        return giftMapper.toDto(gift);
    }

    @Override
    public Optional<GiftDTO> partialUpdate(GiftDTO giftDTO) {
        log.debug("Request to partially update Gift : {}", giftDTO);

        return giftRepository
            .findById(giftDTO.getId())
            .map(
                existingGift -> {
                    giftMapper.partialUpdate(existingGift, giftDTO);

                    return existingGift;
                }
            )
            .map(giftRepository::save)
            .map(giftMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GiftDTO> findAll() {
        log.debug("Request to get all Gifts");
        return giftRepository.findAll().stream().map(giftMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GiftDTO> findOne(Long id) {
        log.debug("Request to get Gift : {}", id);
        return giftRepository.findById(id).map(giftMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Gift : {}", id);
        giftRepository.deleteById(id);
    }
}
