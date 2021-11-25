package com.emclab.voucher.service;

import com.emclab.voucher.service.dto.VoucherDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.emclab.voucher.domain.Voucher}.
 */
public interface VoucherService {
    /**
     * Save a voucher.
     *
     * @param voucherDTO the entity to save.
     * @return the persisted entity.
     */
    VoucherDTO save(VoucherDTO voucherDTO);

    /**
     * Partially updates a voucher.
     *
     * @param voucherDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VoucherDTO> partialUpdate(VoucherDTO voucherDTO);

    /**
     * Get all the vouchers.
     *
     * @return the list of entities.
     */
    List<VoucherDTO> findAll();

    List<VoucherDTO> findByTypeId(Long id);

    /**
     * Get all the vouchers with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VoucherDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" voucher.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VoucherDTO> findOne(Long id);

    /**
     * Delete the "id" voucher.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
