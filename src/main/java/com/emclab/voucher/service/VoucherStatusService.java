package com.emclab.voucher.service;

import com.emclab.voucher.service.dto.VoucherStatusDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.emclab.voucher.domain.VoucherStatus}.
 */
public interface VoucherStatusService {
    /**
     * Save a voucherStatus.
     *
     * @param voucherStatusDTO the entity to save.
     * @return the persisted entity.
     */
    VoucherStatusDTO save(VoucherStatusDTO voucherStatusDTO);

    /**
     * Partially updates a voucherStatus.
     *
     * @param voucherStatusDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VoucherStatusDTO> partialUpdate(VoucherStatusDTO voucherStatusDTO);

    /**
     * Get all the voucherStatuses.
     *
     * @return the list of entities.
     */
    List<VoucherStatusDTO> findAll();

    /**
     * Get the "id" voucherStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VoucherStatusDTO> findOne(Long id);

    /**
     * Delete the "id" voucherStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
