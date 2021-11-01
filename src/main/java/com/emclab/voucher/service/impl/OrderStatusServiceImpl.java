package com.emclab.voucher.service.impl;

import com.emclab.voucher.domain.OrderStatus;
import com.emclab.voucher.repository.OrderStatusRepository;
import com.emclab.voucher.service.OrderStatusService;
import com.emclab.voucher.service.dto.OrderStatusDTO;
import com.emclab.voucher.service.mapper.OrderStatusMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrderStatus}.
 */
@Service
@Transactional
public class OrderStatusServiceImpl implements OrderStatusService {

    private final Logger log = LoggerFactory.getLogger(OrderStatusServiceImpl.class);

    private final OrderStatusRepository orderStatusRepository;

    private final OrderStatusMapper orderStatusMapper;

    public OrderStatusServiceImpl(OrderStatusRepository orderStatusRepository, OrderStatusMapper orderStatusMapper) {
        this.orderStatusRepository = orderStatusRepository;
        this.orderStatusMapper = orderStatusMapper;
    }

    @Override
    public OrderStatusDTO save(OrderStatusDTO orderStatusDTO) {
        log.debug("Request to save OrderStatus : {}", orderStatusDTO);
        OrderStatus orderStatus = orderStatusMapper.toEntity(orderStatusDTO);
        orderStatus = orderStatusRepository.save(orderStatus);
        return orderStatusMapper.toDto(orderStatus);
    }

    @Override
    public Optional<OrderStatusDTO> partialUpdate(OrderStatusDTO orderStatusDTO) {
        log.debug("Request to partially update OrderStatus : {}", orderStatusDTO);

        return orderStatusRepository
            .findById(orderStatusDTO.getId())
            .map(
                existingOrderStatus -> {
                    orderStatusMapper.partialUpdate(existingOrderStatus, orderStatusDTO);

                    return existingOrderStatus;
                }
            )
            .map(orderStatusRepository::save)
            .map(orderStatusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderStatusDTO> findAll() {
        log.debug("Request to get all OrderStatuses");
        return orderStatusRepository.findAll().stream().map(orderStatusMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderStatusDTO> findOne(Long id) {
        log.debug("Request to get OrderStatus : {}", id);
        return orderStatusRepository.findById(id).map(orderStatusMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderStatus : {}", id);
        orderStatusRepository.deleteById(id);
    }
}
