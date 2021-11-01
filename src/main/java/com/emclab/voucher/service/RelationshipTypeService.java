package com.emclab.voucher.service;

import com.emclab.voucher.service.dto.RelationshipTypeDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.emclab.voucher.domain.RelationshipType}.
 */
public interface RelationshipTypeService {
    /**
     * Save a relationshipType.
     *
     * @param relationshipTypeDTO the entity to save.
     * @return the persisted entity.
     */
    RelationshipTypeDTO save(RelationshipTypeDTO relationshipTypeDTO);

    /**
     * Partially updates a relationshipType.
     *
     * @param relationshipTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RelationshipTypeDTO> partialUpdate(RelationshipTypeDTO relationshipTypeDTO);

    /**
     * Get all the relationshipTypes.
     *
     * @return the list of entities.
     */
    List<RelationshipTypeDTO> findAll();

    /**
     * Get the "id" relationshipType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RelationshipTypeDTO> findOne(Long id);

    /**
     * Delete the "id" relationshipType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
