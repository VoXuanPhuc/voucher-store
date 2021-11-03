package com.emclab.voucher.service.impl;

import com.emclab.voucher.domain.StoreUser;
import com.emclab.voucher.repository.StoreUserRepository;
import com.emclab.voucher.service.StoreUserService;
import com.emclab.voucher.service.dto.StoreUserDTO;
import com.emclab.voucher.service.mapper.StoreUserMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StoreUser}.
 */
@Service
@Transactional
public class StoreUserServiceImpl implements StoreUserService {

    private final Logger log = LoggerFactory.getLogger(StoreUserServiceImpl.class);

    private final StoreUserRepository storeUserRepository;

    private final StoreUserMapper storeUserMapper;

    public StoreUserServiceImpl(StoreUserRepository storeUserRepository, StoreUserMapper storeUserMapper) {
        this.storeUserRepository = storeUserRepository;
        this.storeUserMapper = storeUserMapper;
    }

    @Override
    public StoreUserDTO save(StoreUserDTO storeUserDTO) {
        log.debug("Request to save StoreUser : {}", storeUserDTO);
        StoreUser storeUser = storeUserMapper.toEntity(storeUserDTO);
        storeUser = storeUserRepository.save(storeUser);
        return storeUserMapper.toDto(storeUser);
    }

    @Override
    public Optional<StoreUserDTO> partialUpdate(StoreUserDTO storeUserDTO) {
        log.debug("Request to partially update StoreUser : {}", storeUserDTO);

        return storeUserRepository
            .findById(storeUserDTO.getId())
            .map(
                existingStoreUser -> {
                    storeUserMapper.partialUpdate(existingStoreUser, storeUserDTO);

                    return existingStoreUser;
                }
            )
            .map(storeUserRepository::save)
            .map(storeUserMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoreUserDTO> findAll() {
        log.debug("Request to get all StoreUsers");
        return storeUserRepository.findAll().stream().map(storeUserMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StoreUserDTO> findOne(Long id) {
        log.debug("Request to get StoreUser : {}", id);
        return storeUserRepository.findById(id).map(storeUserMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StoreUser : {}", id);
        storeUserRepository.deleteById(id);
    }
}
