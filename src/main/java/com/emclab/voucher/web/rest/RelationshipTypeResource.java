package com.emclab.voucher.web.rest;

import com.emclab.voucher.repository.RelationshipTypeRepository;
import com.emclab.voucher.service.RelationshipTypeService;
import com.emclab.voucher.service.dto.RelationshipTypeDTO;
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
 * REST controller for managing {@link com.emclab.voucher.domain.RelationshipType}.
 */
@RestController
@RequestMapping("/api")
public class RelationshipTypeResource {

    private final Logger log = LoggerFactory.getLogger(RelationshipTypeResource.class);

    private static final String ENTITY_NAME = "relationshipType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RelationshipTypeService relationshipTypeService;

    private final RelationshipTypeRepository relationshipTypeRepository;

    public RelationshipTypeResource(
        RelationshipTypeService relationshipTypeService,
        RelationshipTypeRepository relationshipTypeRepository
    ) {
        this.relationshipTypeService = relationshipTypeService;
        this.relationshipTypeRepository = relationshipTypeRepository;
    }

    /**
     * {@code POST  /relationship-types} : Create a new relationshipType.
     *
     * @param relationshipTypeDTO the relationshipTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new relationshipTypeDTO, or with status {@code 400 (Bad Request)} if the relationshipType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/relationship-types")
    public ResponseEntity<RelationshipTypeDTO> createRelationshipType(@Valid @RequestBody RelationshipTypeDTO relationshipTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save RelationshipType : {}", relationshipTypeDTO);
        if (relationshipTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new relationshipType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RelationshipTypeDTO result = relationshipTypeService.save(relationshipTypeDTO);
        return ResponseEntity
            .created(new URI("/api/relationship-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /relationship-types/:id} : Updates an existing relationshipType.
     *
     * @param id the id of the relationshipTypeDTO to save.
     * @param relationshipTypeDTO the relationshipTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relationshipTypeDTO,
     * or with status {@code 400 (Bad Request)} if the relationshipTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the relationshipTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/relationship-types/{id}")
    public ResponseEntity<RelationshipTypeDTO> updateRelationshipType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RelationshipTypeDTO relationshipTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RelationshipType : {}, {}", id, relationshipTypeDTO);
        if (relationshipTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relationshipTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!relationshipTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RelationshipTypeDTO result = relationshipTypeService.save(relationshipTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, relationshipTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /relationship-types/:id} : Partial updates given fields of an existing relationshipType, field will ignore if it is null
     *
     * @param id the id of the relationshipTypeDTO to save.
     * @param relationshipTypeDTO the relationshipTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relationshipTypeDTO,
     * or with status {@code 400 (Bad Request)} if the relationshipTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the relationshipTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the relationshipTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/relationship-types/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RelationshipTypeDTO> partialUpdateRelationshipType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RelationshipTypeDTO relationshipTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RelationshipType partially : {}, {}", id, relationshipTypeDTO);
        if (relationshipTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relationshipTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!relationshipTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RelationshipTypeDTO> result = relationshipTypeService.partialUpdate(relationshipTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, relationshipTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /relationship-types} : get all the relationshipTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of relationshipTypes in body.
     */
    @GetMapping("/relationship-types")
    public List<RelationshipTypeDTO> getAllRelationshipTypes() {
        log.debug("REST request to get all RelationshipTypes");
        return relationshipTypeService.findAll();
    }

    /**
     * {@code GET  /relationship-types/:id} : get the "id" relationshipType.
     *
     * @param id the id of the relationshipTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the relationshipTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/relationship-types/{id}")
    public ResponseEntity<RelationshipTypeDTO> getRelationshipType(@PathVariable Long id) {
        log.debug("REST request to get RelationshipType : {}", id);
        Optional<RelationshipTypeDTO> relationshipTypeDTO = relationshipTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(relationshipTypeDTO);
    }

    /**
     * {@code DELETE  /relationship-types/:id} : delete the "id" relationshipType.
     *
     * @param id the id of the relationshipTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/relationship-types/{id}")
    public ResponseEntity<Void> deleteRelationshipType(@PathVariable Long id) {
        log.debug("REST request to delete RelationshipType : {}", id);
        relationshipTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
