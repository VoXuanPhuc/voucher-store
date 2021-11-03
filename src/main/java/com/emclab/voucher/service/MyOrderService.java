package com.emclab.voucher.service;

import com.emclab.voucher.service.dto.MyOrderDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.emclab.voucher.domain.MyOrder}.
 */
public interface MyOrderService {
    /**
     * Save a myOrder.
     *
     * @param myOrderDTO the entity to save.
     * @return the persisted entity.
     */
    MyOrderDTO save(MyOrderDTO myOrderDTO);

    /**
     * Partially updates a myOrder.
     *
     * @param myOrderDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MyOrderDTO> partialUpdate(MyOrderDTO myOrderDTO);

    /**
     * Get all the myOrders.
     *
     * @return the list of entities.
     */
    List<MyOrderDTO> findAll();

    /**
     * Get the "id" myOrder.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MyOrderDTO> findOne(Long id);

    /**
     * Delete the "id" myOrder.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
