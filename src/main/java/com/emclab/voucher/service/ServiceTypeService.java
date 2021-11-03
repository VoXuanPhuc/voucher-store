package com.emclab.voucher.service;

import com.emclab.voucher.service.dto.ServiceTypeDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.emclab.voucher.domain.ServiceType}.
 */
public interface ServiceTypeService {
    /**
     * Save a serviceType.
     *
     * @param serviceTypeDTO the entity to save.
     * @return the persisted entity.
     */
    ServiceTypeDTO save(ServiceTypeDTO serviceTypeDTO);

    /**
     * Partially updates a serviceType.
     *
     * @param serviceTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ServiceTypeDTO> partialUpdate(ServiceTypeDTO serviceTypeDTO);

    /**
     * Get all the serviceTypes.
     *
     * @return the list of entities.
     */
    List<ServiceTypeDTO> findAll();

    /**
     * Get the "id" serviceType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceTypeDTO> findOne(Long id);

    /**
     * Delete the "id" serviceType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
