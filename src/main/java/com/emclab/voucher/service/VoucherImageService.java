package com.emclab.voucher.service;

import com.emclab.voucher.domain.Voucher;
import com.emclab.voucher.service.dto.VoucherImageDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.emclab.voucher.domain.VoucherImage}.
 */
public interface VoucherImageService {
    /**
     * Save a voucherImage.
     *
     * @param voucherImageDTO the entity to save.
     * @return the persisted entity.
     */
    VoucherImageDTO save(VoucherImageDTO voucherImageDTO);

    /**
     * Partially updates a voucherImage.
     *
     * @param voucherImageDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VoucherImageDTO> partialUpdate(VoucherImageDTO voucherImageDTO);

    /**
     * Get all the voucherImages.
     *
     * @return the list of entities.
     */
    List<VoucherImageDTO> findAll();

    /**
     * Get the "id" voucherImage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VoucherImageDTO> findOne(Long id);

    /**
     * Delete the "id" voucherImage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get all voucherImage for voucher
     * */

    List<VoucherImageDTO> findByVoucherId(Long id);
}
