package com.emclab.voucher.service;

import com.emclab.voucher.service.dto.GiftDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.emclab.voucher.domain.Gift}.
 */
public interface GiftService {
    /**
     * Save a gift.
     *
     * @param giftDTO the entity to save.
     * @return the persisted entity.
     */
    GiftDTO save(GiftDTO giftDTO);

    /**
     * Partially updates a gift.
     *
     * @param giftDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GiftDTO> partialUpdate(GiftDTO giftDTO);

    /**
     * Get all the gifts.
     *
     * @return the list of entities.
     */
    List<GiftDTO> findAll();

    /**
     * Get the "id" gift.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GiftDTO> findOne(Long id);

    /**
     * Delete the "id" gift.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
