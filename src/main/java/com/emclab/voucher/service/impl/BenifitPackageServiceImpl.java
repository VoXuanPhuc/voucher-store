package com.emclab.voucher.service.impl;

import com.emclab.voucher.domain.BenifitPackage;
import com.emclab.voucher.repository.BenifitPackageRepository;
import com.emclab.voucher.service.BenifitPackageService;
import com.emclab.voucher.service.dto.BenifitPackageDTO;
import com.emclab.voucher.service.mapper.BenifitPackageMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BenifitPackage}.
 */
@Service
@Transactional
public class BenifitPackageServiceImpl implements BenifitPackageService {

    private final Logger log = LoggerFactory.getLogger(BenifitPackageServiceImpl.class);

    private final BenifitPackageRepository benifitPackageRepository;

    private final BenifitPackageMapper benifitPackageMapper;

    public BenifitPackageServiceImpl(BenifitPackageRepository benifitPackageRepository, BenifitPackageMapper benifitPackageMapper) {
        this.benifitPackageRepository = benifitPackageRepository;
        this.benifitPackageMapper = benifitPackageMapper;
    }

    @Override
    public BenifitPackageDTO save(BenifitPackageDTO benifitPackageDTO) {
        log.debug("Request to save BenifitPackage : {}", benifitPackageDTO);
        BenifitPackage benifitPackage = benifitPackageMapper.toEntity(benifitPackageDTO);
        benifitPackage = benifitPackageRepository.save(benifitPackage);
        return benifitPackageMapper.toDto(benifitPackage);
    }

    @Override
    public Optional<BenifitPackageDTO> partialUpdate(BenifitPackageDTO benifitPackageDTO) {
        log.debug("Request to partially update BenifitPackage : {}", benifitPackageDTO);

        return benifitPackageRepository
            .findById(benifitPackageDTO.getId())
            .map(
                existingBenifitPackage -> {
                    benifitPackageMapper.partialUpdate(existingBenifitPackage, benifitPackageDTO);

                    return existingBenifitPackage;
                }
            )
            .map(benifitPackageRepository::save)
            .map(benifitPackageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BenifitPackageDTO> findAll() {
        log.debug("Request to get all BenifitPackages");
        return benifitPackageRepository
            .findAll()
            .stream()
            .map(benifitPackageMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BenifitPackageDTO> findOne(Long id) {
        log.debug("Request to get BenifitPackage : {}", id);
        return benifitPackageRepository.findById(id).map(benifitPackageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BenifitPackage : {}", id);
        benifitPackageRepository.deleteById(id);
    }
}
