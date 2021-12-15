package com.emclab.voucher.service.impl;

import com.emclab.voucher.domain.ServiceType;
import com.emclab.voucher.domain.Voucher;
import com.emclab.voucher.repository.ServiceTypeRepository;
import com.emclab.voucher.repository.VoucherRepository;
import com.emclab.voucher.service.CommonService;
import com.emclab.voucher.service.VoucherService;
import com.emclab.voucher.service.dto.PaginationResponse;
import com.emclab.voucher.service.dto.VoucherDTO;
import com.emclab.voucher.service.mapper.VoucherMapper;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
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

    private final ServiceTypeRepository typeReponsitory;

    private final VoucherMapper voucherMapper;

    @Autowired
    private CommonService commonService;

    public VoucherServiceImpl(VoucherRepository voucherRepository, VoucherMapper voucherMapper, ServiceTypeRepository typeReponsitory) {
        this.voucherRepository = voucherRepository;
        this.voucherMapper = voucherMapper;
        this.typeReponsitory = typeReponsitory;
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

    @Override
    public List<VoucherDTO> findByTypeId(Long id) {
        log.debug("Request to get vouchers by type");

        ServiceType type = typeReponsitory.findById(id).get();

        return voucherRepository.findByType(type).stream().map(voucherMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
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

    @Override
    public PaginationResponse findWithPaging(Map<String, Object> params) {
        log.debug("Request to get all Stores with paging");

        int totalItems = 0;
        List<Voucher> vouchers = null;

        params = commonService.updateParam(params, 9);
        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        Pageable pageable = PageRequest.of(page - 1, limit);

        if (params.get("type") == null && params.get("search") == null && params.get("sort") == null) {
            totalItems = voucherRepository.findAll().size();
            vouchers = voucherRepository.findAll(pageable).getContent();
        } else if (params.get("type") == null && params.get("search") == null && params.get("sort") != null) {
            Order order = getOrder(params.get("sort").toString());
            pageable = PageRequest.of(page - 1, limit, Sort.by(order));
            totalItems = voucherRepository.findAll().size();
            vouchers = voucherRepository.findAll(pageable).getContent();
        } else if (params.get("type") == null && params.get("search") != null && params.get("sort") == null) {
            String name = params.get("search").toString();
            totalItems = voucherRepository.findByNameContaining(name).size();
            vouchers = voucherRepository.findByNameContaining(name, pageable);
        } else if (params.get("type") == null && params.get("search") != null && params.get("sort") != null) {
            String name = params.get("search").toString();
            Order order = getOrder(params.get("sort").toString());
            pageable = PageRequest.of(page - 1, limit, Sort.by(order));
            totalItems = voucherRepository.findByNameContaining(name).size();
            vouchers = voucherRepository.findByNameContaining(name, pageable);
        } else if (params.get("type") != null && params.get("search") == null && params.get("sort") == null) {
            long typeId = Integer.parseInt(params.get("type").toString());
            ServiceType type = typeReponsitory.findById(typeId).get();
            totalItems = voucherRepository.findByType(type).size();
            vouchers = voucherRepository.findByType(type, pageable);
        } else if (params.get("type") != null && params.get("search") == null && params.get("sort") != null) {
            long typeId = Integer.parseInt(params.get("type").toString());
            ServiceType type = typeReponsitory.findById(typeId).get();
            Order order = getOrder(params.get("sort").toString());
            pageable = PageRequest.of(page - 1, limit, Sort.by(order));
            totalItems = voucherRepository.findByType(type).size();
            vouchers = voucherRepository.findByType(type, pageable);
        } else if (params.get("type") != null && params.get("search") != null && params.get("sort") == null) {
            long typeId = Integer.parseInt(params.get("type").toString());
            ServiceType type = typeReponsitory.findById(typeId).get();
            String name = params.get("search").toString();
            totalItems = voucherRepository.findByTypeAndNameContaining(type, name).size();
            vouchers = voucherRepository.findByTypeAndNameContaining(type, name, pageable);
        } else if (params.get("type") != null && params.get("search") != null && params.get("sort") != null) {
            long typeId = Integer.parseInt(params.get("type").toString());
            ServiceType type = typeReponsitory.findById(typeId).get();
            String name = params.get("search").toString();
            Order order = getOrder(params.get("sort").toString());
            pageable = PageRequest.of(page - 1, limit, Sort.by(order));
            totalItems = voucherRepository.findByTypeAndNameContaining(type, name).size();
            vouchers = voucherRepository.findByTypeAndNameContaining(type, name, pageable);
        }

        List<VoucherDTO> voucherDTOs = vouchers.stream().map(voucherMapper::toDto).collect(Collectors.toCollection(LinkedList::new));

        List<Object> items = new ArrayList<>(voucherDTOs);

        return commonService.findWithPaging(params, totalItems, items);
    }

    public Order getOrder(String sortOrder) {
        String[] _sort = sortOrder.split(Pattern.quote("."));
        Sort.Direction direction = null;

        if (_sort[1].equals("asc")) {
            direction = Sort.Direction.ASC;
        } else if (_sort[1].equals("desc")) {
            direction = Sort.Direction.DESC;
        }

        return new Order(direction, _sort[0]);
    }
}
