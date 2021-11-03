package com.emclab.voucher.service;

import com.emclab.voucher.service.dto.FeedbackImageDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.emclab.voucher.domain.FeedbackImage}.
 */
public interface FeedbackImageService {
    /**
     * Save a feedbackImage.
     *
     * @param feedbackImageDTO the entity to save.
     * @return the persisted entity.
     */
    FeedbackImageDTO save(FeedbackImageDTO feedbackImageDTO);

    /**
     * Partially updates a feedbackImage.
     *
     * @param feedbackImageDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FeedbackImageDTO> partialUpdate(FeedbackImageDTO feedbackImageDTO);

    /**
     * Get all the feedbackImages.
     *
     * @return the list of entities.
     */
    List<FeedbackImageDTO> findAll();

    /**
     * Get the "id" feedbackImage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FeedbackImageDTO> findOne(Long id);

    /**
     * Delete the "id" feedbackImage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
