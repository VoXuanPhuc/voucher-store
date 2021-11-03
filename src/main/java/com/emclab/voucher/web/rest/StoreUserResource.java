package com.emclab.voucher.web.rest;

import com.emclab.voucher.repository.StoreUserRepository;
import com.emclab.voucher.service.StoreUserService;
import com.emclab.voucher.service.dto.StoreUserDTO;
import com.emclab.voucher.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.emclab.voucher.domain.StoreUser}.
 */
@RestController
@RequestMapping("/api")
public class StoreUserResource {

    private final Logger log = LoggerFactory.getLogger(StoreUserResource.class);

    private static final String ENTITY_NAME = "storeUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StoreUserService storeUserService;

    private final StoreUserRepository storeUserRepository;

    public StoreUserResource(StoreUserService storeUserService, StoreUserRepository storeUserRepository) {
        this.storeUserService = storeUserService;
        this.storeUserRepository = storeUserRepository;
    }

    /**
     * {@code POST  /store-users} : Create a new storeUser.
     *
     * @param storeUserDTO the storeUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new storeUserDTO, or with status {@code 400 (Bad Request)} if the storeUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/store-users")
    public ResponseEntity<StoreUserDTO> createStoreUser(@RequestBody StoreUserDTO storeUserDTO) throws URISyntaxException {
        log.debug("REST request to save StoreUser : {}", storeUserDTO);
        if (storeUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new storeUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StoreUserDTO result = storeUserService.save(storeUserDTO);
        return ResponseEntity
            .created(new URI("/api/store-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /store-users/:id} : Updates an existing storeUser.
     *
     * @param id the id of the storeUserDTO to save.
     * @param storeUserDTO the storeUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated storeUserDTO,
     * or with status {@code 400 (Bad Request)} if the storeUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the storeUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/store-users/{id}")
    public ResponseEntity<StoreUserDTO> updateStoreUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StoreUserDTO storeUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to update StoreUser : {}, {}", id, storeUserDTO);
        if (storeUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, storeUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!storeUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StoreUserDTO result = storeUserService.save(storeUserDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, storeUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /store-users/:id} : Partial updates given fields of an existing storeUser, field will ignore if it is null
     *
     * @param id the id of the storeUserDTO to save.
     * @param storeUserDTO the storeUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated storeUserDTO,
     * or with status {@code 400 (Bad Request)} if the storeUserDTO is not valid,
     * or with status {@code 404 (Not Found)} if the storeUserDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the storeUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/store-users/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<StoreUserDTO> partialUpdateStoreUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StoreUserDTO storeUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update StoreUser partially : {}, {}", id, storeUserDTO);
        if (storeUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, storeUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!storeUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StoreUserDTO> result = storeUserService.partialUpdate(storeUserDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, storeUserDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /store-users} : get all the storeUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of storeUsers in body.
     */
    @GetMapping("/store-users")
    public List<StoreUserDTO> getAllStoreUsers() {
        log.debug("REST request to get all StoreUsers");
        return storeUserService.findAll();
    }

    /**
     * {@code GET  /store-users/:id} : get the "id" storeUser.
     *
     * @param id the id of the storeUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the storeUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/store-users/{id}")
    public ResponseEntity<StoreUserDTO> getStoreUser(@PathVariable Long id) {
        log.debug("REST request to get StoreUser : {}", id);
        Optional<StoreUserDTO> storeUserDTO = storeUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(storeUserDTO);
    }

    /**
     * {@code DELETE  /store-users/:id} : delete the "id" storeUser.
     *
     * @param id the id of the storeUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/store-users/{id}")
    public ResponseEntity<Void> deleteStoreUser(@PathVariable Long id) {
        log.debug("REST request to delete StoreUser : {}", id);
        storeUserService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
