package com.emclab.voucher.service;

import com.emclab.voucher.service.dto.BenifitPackageDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.emclab.voucher.domain.BenifitPackage}.
 */
public interface BenifitPackageService {
    /**
     * Save a benifitPackage.
     *
     * @param benifitPackageDTO the entity to save.
     * @return the persisted entity.
     */
    BenifitPackageDTO save(BenifitPackageDTO benifitPackageDTO);

    /**
     * Partially updates a benifitPackage.
     *
     * @param benifitPackageDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BenifitPackageDTO> partialUpdate(BenifitPackageDTO benifitPackageDTO);

    /**
     * Get all the benifitPackages.
     *
     * @return the list of entities.
     */
    List<BenifitPackageDTO> findAll();

    /**
     * Get the "id" benifitPackage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BenifitPackageDTO> findOne(Long id);

    /**
     * Delete the "id" benifitPackage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
