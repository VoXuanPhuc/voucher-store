package com.emclab.voucher.service.impl;

import com.emclab.voucher.domain.VoucherStatus;
import com.emclab.voucher.repository.VoucherStatusRepository;
import com.emclab.voucher.service.VoucherStatusService;
import com.emclab.voucher.service.dto.VoucherStatusDTO;
import com.emclab.voucher.service.mapper.VoucherStatusMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link VoucherStatus}.
 */
@Service
@Transactional
public class VoucherStatusServiceImpl implements VoucherStatusService {

    private final Logger log = LoggerFactory.getLogger(VoucherStatusServiceImpl.class);

    private final VoucherStatusRepository voucherStatusRepository;

    private final VoucherStatusMapper voucherStatusMapper;

    public VoucherStatusServiceImpl(VoucherStatusRepository voucherStatusRepository, VoucherStatusMapper voucherStatusMapper) {
        this.voucherStatusRepository = voucherStatusRepository;
        this.voucherStatusMapper = voucherStatusMapper;
    }

    @Override
    public VoucherStatusDTO save(VoucherStatusDTO voucherStatusDTO) {
        log.debug("Request to save VoucherStatus : {}", voucherStatusDTO);
        VoucherStatus voucherStatus = voucherStatusMapper.toEntity(voucherStatusDTO);
        voucherStatus = voucherStatusRepository.save(voucherStatus);
        return voucherStatusMapper.toDto(voucherStatus);
    }

    @Override
    public Optional<VoucherStatusDTO> partialUpdate(VoucherStatusDTO voucherStatusDTO) {
        log.debug("Request to partially update VoucherStatus : {}", voucherStatusDTO);

        return voucherStatusRepository
            .findById(voucherStatusDTO.getId())
            .map(
                existingVoucherStatus -> {
                    voucherStatusMapper.partialUpdate(existingVoucherStatus, voucherStatusDTO);

                    return existingVoucherStatus;
                }
            )
            .map(voucherStatusRepository::save)
            .map(voucherStatusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VoucherStatusDTO> findAll() {
        log.debug("Request to get all VoucherStatuses");
        return voucherStatusRepository.findAll().stream().map(voucherStatusMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VoucherStatusDTO> findOne(Long id) {
        log.debug("Request to get VoucherStatus : {}", id);
        return voucherStatusRepository.findById(id).map(voucherStatusMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VoucherStatus : {}", id);
        voucherStatusRepository.deleteById(id);
    }
}
