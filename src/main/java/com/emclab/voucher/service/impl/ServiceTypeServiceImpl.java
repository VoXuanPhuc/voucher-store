package com.emclab.voucher.service.impl;

import com.emclab.voucher.domain.ServiceType;
import com.emclab.voucher.repository.ServiceTypeRepository;
import com.emclab.voucher.service.ServiceTypeService;
import com.emclab.voucher.service.dto.ServiceTypeDTO;
import com.emclab.voucher.service.mapper.ServiceTypeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ServiceType}.
 */
@Service
@Transactional
public class ServiceTypeServiceImpl implements ServiceTypeService {

    private final Logger log = LoggerFactory.getLogger(ServiceTypeServiceImpl.class);

    private final ServiceTypeRepository serviceTypeRepository;

    private final ServiceTypeMapper serviceTypeMapper;

    public ServiceTypeServiceImpl(ServiceTypeRepository serviceTypeRepository, ServiceTypeMapper serviceTypeMapper) {
        this.serviceTypeRepository = serviceTypeRepository;
        this.serviceTypeMapper = serviceTypeMapper;
    }

    @Override
    public ServiceTypeDTO save(ServiceTypeDTO serviceTypeDTO) {
        log.debug("Request to save ServiceType : {}", serviceTypeDTO);
        ServiceType serviceType = serviceTypeMapper.toEntity(serviceTypeDTO);
        serviceType = serviceTypeRepository.save(serviceType);
        return serviceTypeMapper.toDto(serviceType);
    }

    @Override
    public Optional<ServiceTypeDTO> partialUpdate(ServiceTypeDTO serviceTypeDTO) {
        log.debug("Request to partially update ServiceType : {}", serviceTypeDTO);

        return serviceTypeRepository
            .findById(serviceTypeDTO.getId())
            .map(
                existingServiceType -> {
                    serviceTypeMapper.partialUpdate(existingServiceType, serviceTypeDTO);

                    return existingServiceType;
                }
            )
            .map(serviceTypeRepository::save)
            .map(serviceTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceTypeDTO> findAll() {
        log.debug("Request to get all ServiceTypes");
        return serviceTypeRepository.findAll().stream().map(serviceTypeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceTypeDTO> findOne(Long id) {
        log.debug("Request to get ServiceType : {}", id);
        return serviceTypeRepository.findById(id).map(serviceTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceType : {}", id);
        serviceTypeRepository.deleteById(id);
    }
}
