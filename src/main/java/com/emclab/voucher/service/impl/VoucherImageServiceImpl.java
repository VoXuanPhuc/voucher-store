package com.emclab.voucher.service.impl;

import com.emclab.voucher.domain.Voucher;
import com.emclab.voucher.domain.VoucherImage;
import com.emclab.voucher.repository.VoucherImageRepository;
import com.emclab.voucher.repository.VoucherRepository;
import com.emclab.voucher.service.VoucherImageService;
import com.emclab.voucher.service.dto.VoucherImageDTO;
import com.emclab.voucher.service.mapper.VoucherImageMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link VoucherImage}.
 */
@Service
@Transactional
public class VoucherImageServiceImpl implements VoucherImageService {

    private final Logger log = LoggerFactory.getLogger(VoucherImageServiceImpl.class);

    private final VoucherImageRepository voucherImageRepository;

    private final VoucherRepository voucherRepository;

    private final VoucherImageMapper voucherImageMapper;

    public VoucherImageServiceImpl(
        VoucherImageRepository voucherImageRepository,
        VoucherImageMapper voucherImageMapper,
        VoucherRepository voucherRepository
    ) {
        this.voucherImageRepository = voucherImageRepository;
        this.voucherImageMapper = voucherImageMapper;
        this.voucherRepository = voucherRepository;
    }

    @Override
    public VoucherImageDTO save(VoucherImageDTO voucherImageDTO) {
        log.debug("Request to save VoucherImage : {}", voucherImageDTO);
        VoucherImage voucherImage = voucherImageMapper.toEntity(voucherImageDTO);
        voucherImage = voucherImageRepository.save(voucherImage);
        return voucherImageMapper.toDto(voucherImage);
    }

    @Override
    public Optional<VoucherImageDTO> partialUpdate(VoucherImageDTO voucherImageDTO) {
        log.debug("Request to partially update VoucherImage : {}", voucherImageDTO);

        return voucherImageRepository
            .findById(voucherImageDTO.getId())
            .map(
                existingVoucherImage -> {
                    voucherImageMapper.partialUpdate(existingVoucherImage, voucherImageDTO);

                    return existingVoucherImage;
                }
            )
            .map(voucherImageRepository::save)
            .map(voucherImageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VoucherImageDTO> findAll() {
        log.debug("Request to get all VoucherImages");
        return voucherImageRepository.findAll().stream().map(voucherImageMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VoucherImageDTO> findOne(Long id) {
        log.debug("Request to get VoucherImage : {}", id);
        return voucherImageRepository.findById(id).map(voucherImageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VoucherImage : {}", id);
        voucherImageRepository.deleteById(id);
    }

    @Override
    public List<VoucherImageDTO> findByVoucherId(Long id) {
        Voucher voucher = voucherRepository.getOne(id);

        return voucherImageRepository
            .findByVoucher(voucher)
            .stream()
            .map(voucherImageMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
