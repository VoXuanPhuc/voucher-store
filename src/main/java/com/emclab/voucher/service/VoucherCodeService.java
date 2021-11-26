package com.emclab.voucher.service;

import com.emclab.voucher.domain.Voucher;
import com.emclab.voucher.domain.VoucherStatus;
import com.emclab.voucher.service.dto.VoucherCodeDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.emclab.voucher.domain.VoucherCode}.
 */
public interface VoucherCodeService {
    /**
     * Save a voucherCode.
     *
     * @param voucherCodeDTO the entity to save.
     * @return the persisted entity.
     */
    VoucherCodeDTO save(VoucherCodeDTO voucherCodeDTO);

    /**
     * Partially updates a voucherCode.
     *
     * @param voucherCodeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VoucherCodeDTO> partialUpdate(VoucherCodeDTO voucherCodeDTO);

    /**
     * Get all the voucherCodes.
     *
     * @return the list of entities.
     */
    List<VoucherCodeDTO> findAll();

    /**
     * Get the "id" voucherCode.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VoucherCodeDTO> findOne(Long id);

    /**
     * Delete the "id" voucherCode.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Long countVoucherCodeByVoucher(Voucher voucher, VoucherStatus voucherStatus);
}
