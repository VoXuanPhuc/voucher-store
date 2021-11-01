package com.emclab.voucher.service;

import com.emclab.voucher.service.dto.OrderStatusDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.emclab.voucher.domain.OrderStatus}.
 */
public interface OrderStatusService {
    /**
     * Save a orderStatus.
     *
     * @param orderStatusDTO the entity to save.
     * @return the persisted entity.
     */
    OrderStatusDTO save(OrderStatusDTO orderStatusDTO);

    /**
     * Partially updates a orderStatus.
     *
     * @param orderStatusDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrderStatusDTO> partialUpdate(OrderStatusDTO orderStatusDTO);

    /**
     * Get all the orderStatuses.
     *
     * @return the list of entities.
     */
    List<OrderStatusDTO> findAll();

    /**
     * Get the "id" orderStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderStatusDTO> findOne(Long id);

    /**
     * Delete the "id" orderStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
