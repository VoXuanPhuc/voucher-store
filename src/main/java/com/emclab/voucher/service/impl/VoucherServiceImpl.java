package com.emclab.voucher.service.impl;

import com.emclab.voucher.domain.Voucher;
import com.emclab.voucher.repository.VoucherRepository;
import com.emclab.voucher.service.VoucherService;
import com.emclab.voucher.service.dto.VoucherDTO;
import com.emclab.voucher.service.mapper.VoucherMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Voucher}.
 */
@Service
@Transactional
public class VoucherServiceImpl implements VoucherService {

    private final Logger log = LoggerFactory.getLogger(VoucherServiceImpl.class);

    private final VoucherRepository voucherRepository;

    private final VoucherMapper voucherMapper;

    public VoucherServiceImpl(VoucherRepository voucherRepository, VoucherMapper voucherMapper) {
        this.voucherRepository = voucherRepository;
        this.voucherMapper = voucherMapper;
    }

    @Override
    public VoucherDTO save(VoucherDTO voucherDTO) {
        log.debug("Request to save Voucher : {}", voucherDTO);
        Voucher voucher = voucherMapper.toEntity(voucherDTO);
        voucher = voucherRepository.save(voucher);
        return voucherMapper.toDto(voucher);
    }

    @Override
    public Optional<VoucherDTO> partialUpdate(VoucherDTO voucherDTO) {
        log.debug("Request to partially update Voucher : {}", voucherDTO);

        return voucherRepository
            .findById(voucherDTO.getId())
            .map(
                existingVoucher -> {
                    voucherMapper.partialUpdate(existingVoucher, voucherDTO);

                    return existingVoucher;
                }
            )
            .map(voucherRepository::save)
            .map(voucherMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VoucherDTO> findAll() {
        log.debug("Request to get all Vouchers");
        return voucherRepository
            .findAllWithEagerRelationships()
            .stream()
            .map(voucherMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<VoucherDTO> findAllWithEagerRelationships(Pageable pageable) {
        return voucherRepository.findAllWithEagerRelationships(pageable).map(voucherMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VoucherDTO> findOne(Long id) {
        log.debug("Request to get Voucher : {}", id);
        return voucherRepository.findOneWithEagerRelationships(id).map(voucherMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Voucher : {}", id);
        voucherRepository.deleteById(id);
    }
}
