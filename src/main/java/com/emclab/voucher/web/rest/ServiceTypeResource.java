package com.emclab.voucher.web.rest;

import com.emclab.voucher.repository.ServiceTypeRepository;
import com.emclab.voucher.service.ServiceTypeService;
import com.emclab.voucher.service.dto.ServiceTypeDTO;
import com.emclab.voucher.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.emclab.voucher.domain.ServiceType}.
 */
@RestController
@RequestMapping("/api")
public class ServiceTypeResource {

    private final Logger log = LoggerFactory.getLogger(ServiceTypeResource.class);

    private static final String ENTITY_NAME = "serviceType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceTypeService serviceTypeService;

    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceTypeResource(ServiceTypeService serviceTypeService, ServiceTypeRepository serviceTypeRepository) {
        this.serviceTypeService = serviceTypeService;
        this.serviceTypeRepository = serviceTypeRepository;
    }

    /**
     * {@code POST  /service-types} : Create a new serviceType.
     *
     * @param serviceTypeDTO the serviceTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceTypeDTO, or with status {@code 400 (Bad Request)} if the serviceType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-types")
    public ResponseEntity<ServiceTypeDTO> createServiceType(@Valid @RequestBody ServiceTypeDTO serviceTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceType : {}", serviceTypeDTO);
        if (serviceTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceTypeDTO result = serviceTypeService.save(serviceTypeDTO);
        return ResponseEntity
            .created(new URI("/api/service-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-types/:id} : Updates an existing serviceType.
     *
     * @param id the id of the serviceTypeDTO to save.
     * @param serviceTypeDTO the serviceTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceTypeDTO,
     * or with status {@code 400 (Bad Request)} if the serviceTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-types/{id}")
    public ResponseEntity<ServiceTypeDTO> updateServiceType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ServiceTypeDTO serviceTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ServiceType : {}, {}", id, serviceTypeDTO);
        if (serviceTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ServiceTypeDTO result = serviceTypeService.save(serviceTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, serviceTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /service-types/:id} : Partial updates given fields of an existing serviceType, field will ignore if it is null
     *
     * @param id the id of the serviceTypeDTO to save.
     * @param serviceTypeDTO the serviceTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceTypeDTO,
     * or with status {@code 400 (Bad Request)} if the serviceTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the serviceTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the serviceTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/service-types/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ServiceTypeDTO> partialUpdateServiceType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ServiceTypeDTO serviceTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ServiceType partially : {}, {}", id, serviceTypeDTO);
        if (serviceTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServiceTypeDTO> result = serviceTypeService.partialUpdate(serviceTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, serviceTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /service-types} : get all the serviceTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceTypes in body.
     */
    @GetMapping("/service-types")
    public List<ServiceTypeDTO> getAllServiceTypes() {
        log.debug("REST request to get all ServiceTypes");
        return serviceTypeService.findAll();
    }

    /**
     * {@code GET  /service-types/:id} : get the "id" serviceType.
     *
     * @param id the id of the serviceTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-types/{id}")
    public ResponseEntity<ServiceTypeDTO> getServiceType(@PathVariable Long id) {
        log.debug("REST request to get ServiceType : {}", id);
        Optional<ServiceTypeDTO> serviceTypeDTO = serviceTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceTypeDTO);
    }

    /**
     * {@code DELETE  /service-types/:id} : delete the "id" serviceType.
     *
     * @param id the id of the serviceTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-types/{id}")
    public ResponseEntity<Void> deleteServiceType(@PathVariable Long id) {
        log.debug("REST request to delete ServiceType : {}", id);
        serviceTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
