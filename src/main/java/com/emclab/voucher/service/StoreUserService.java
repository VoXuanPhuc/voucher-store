package com.emclab.voucher.service;

import com.emclab.voucher.service.dto.StoreUserDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.emclab.voucher.domain.StoreUser}.
 */
public interface StoreUserService {
    /**
     * Save a storeUser.
     *
     * @param storeUserDTO the entity to save.
     * @return the persisted entity.
     */
    StoreUserDTO save(StoreUserDTO storeUserDTO);

    /**
     * Partially updates a storeUser.
     *
     * @param storeUserDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StoreUserDTO> partialUpdate(StoreUserDTO storeUserDTO);

    /**
     * Get all the storeUsers.
     *
     * @return the list of entities.
     */
    List<StoreUserDTO> findAll();

    /**
     * Get the "id" storeUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StoreUserDTO> findOne(Long id);

    /**
     * Delete the "id" storeUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
