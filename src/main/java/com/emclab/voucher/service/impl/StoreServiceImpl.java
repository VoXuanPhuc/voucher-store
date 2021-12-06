package com.emclab.voucher.service.impl;

import com.emclab.voucher.domain.Store;
import com.emclab.voucher.repository.StoreRepository;
import com.emclab.voucher.service.CommonService;
import com.emclab.voucher.service.StoreService;
import com.emclab.voucher.service.dto.PaginationResponse;
import com.emclab.voucher.service.dto.StoreDTO;
import com.emclab.voucher.service.mapper.StoreMapper;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Store}.
 */
@Service
@Transactional
public class StoreServiceImpl implements StoreService {

    private final Logger log = LoggerFactory.getLogger(StoreServiceImpl.class);

    private final StoreRepository storeRepository;

    private final StoreMapper storeMapper;

    @Autowired
    private CommonService commonService;

    public StoreServiceImpl(StoreRepository storeRepository, StoreMapper storeMapper) {
        this.storeRepository = storeRepository;
        this.storeMapper = storeMapper;
    }

    @Override
    public StoreDTO save(StoreDTO storeDTO) {
        log.debug("Request to save Store : {}", storeDTO);
        Store store = storeMapper.toEntity(storeDTO);
        store = storeRepository.save(store);
        return storeMapper.toDto(store);
    }

    @Override
    public Optional<StoreDTO> partialUpdate(StoreDTO storeDTO) {
        log.debug("Request to partially update Store : {}", storeDTO);

        return storeRepository
            .findById(storeDTO.getId())
            .map(
                existingStore -> {
                    storeMapper.partialUpdate(existingStore, storeDTO);

                    return existingStore;
                }
            )
            .map(storeRepository::save)
            .map(storeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoreDTO> findAll() {
        log.debug("Request to get all Stores");
        return storeRepository.findAll().stream().map(storeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponse findAllWithPaging(Map<String, Object> param) {
        log.debug("Request to get all Stores with paging");

        param = commonService.updateParam(param, 6);
        int page = Integer.parseInt(param.get("page").toString());
        int limit = Integer.parseInt(param.get("limit").toString());

        int totalItems = findAll().size();

        Pageable pageable = PageRequest.of(page - 1, limit);

        List<StoreDTO> stores = storeRepository
            .findAll(pageable)
            .stream()
            .map(storeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));

        List<Object> items = new ArrayList<>(stores);

        return commonService.findWithPaging(param, totalItems, items);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StoreDTO> findOne(Long id) {
        log.debug("Request to get Store : {}", id);
        return storeRepository.findById(id).map(storeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Store : {}", id);
        storeRepository.deleteById(id);
    }
}
