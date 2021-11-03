package com.emclab.voucher.web.rest;

import com.emclab.voucher.repository.VoucherStatusRepository;
import com.emclab.voucher.service.VoucherStatusService;
import com.emclab.voucher.service.dto.VoucherStatusDTO;
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
 * REST controller for managing {@link com.emclab.voucher.domain.VoucherStatus}.
 */
@RestController
@RequestMapping("/api")
public class VoucherStatusResource {

    private final Logger log = LoggerFactory.getLogger(VoucherStatusResource.class);

    private static final String ENTITY_NAME = "voucherStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VoucherStatusService voucherStatusService;

    private final VoucherStatusRepository voucherStatusRepository;

    public VoucherStatusResource(VoucherStatusService voucherStatusService, VoucherStatusRepository voucherStatusRepository) {
        this.voucherStatusService = voucherStatusService;
        this.voucherStatusRepository = voucherStatusRepository;
    }

    /**
     * {@code POST  /voucher-statuses} : Create a new voucherStatus.
     *
     * @param voucherStatusDTO the voucherStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new voucherStatusDTO, or with status {@code 400 (Bad Request)} if the voucherStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/voucher-statuses")
    public ResponseEntity<VoucherStatusDTO> createVoucherStatus(@Valid @RequestBody VoucherStatusDTO voucherStatusDTO)
        throws URISyntaxException {
        log.debug("REST request to save VoucherStatus : {}", voucherStatusDTO);
        if (voucherStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new voucherStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VoucherStatusDTO result = voucherStatusService.save(voucherStatusDTO);
        return ResponseEntity
            .created(new URI("/api/voucher-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /voucher-statuses/:id} : Updates an existing voucherStatus.
     *
     * @param id the id of the voucherStatusDTO to save.
     * @param voucherStatusDTO the voucherStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voucherStatusDTO,
     * or with status {@code 400 (Bad Request)} if the voucherStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the voucherStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/voucher-statuses/{id}")
    public ResponseEntity<VoucherStatusDTO> updateVoucherStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VoucherStatusDTO voucherStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VoucherStatus : {}, {}", id, voucherStatusDTO);
        if (voucherStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voucherStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voucherStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VoucherStatusDTO result = voucherStatusService.save(voucherStatusDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, voucherStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /voucher-statuses/:id} : Partial updates given fields of an existing voucherStatus, field will ignore if it is null
     *
     * @param id the id of the voucherStatusDTO to save.
     * @param voucherStatusDTO the voucherStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voucherStatusDTO,
     * or with status {@code 400 (Bad Request)} if the voucherStatusDTO is not valid,
     * or with status {@code 404 (Not Found)} if the voucherStatusDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the voucherStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/voucher-statuses/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<VoucherStatusDTO> partialUpdateVoucherStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VoucherStatusDTO voucherStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VoucherStatus partially : {}, {}", id, voucherStatusDTO);
        if (voucherStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voucherStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voucherStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VoucherStatusDTO> result = voucherStatusService.partialUpdate(voucherStatusDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, voucherStatusDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /voucher-statuses} : get all the voucherStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of voucherStatuses in body.
     */
    @GetMapping("/voucher-statuses")
    public List<VoucherStatusDTO> getAllVoucherStatuses() {
        log.debug("REST request to get all VoucherStatuses");
        return voucherStatusService.findAll();
    }

    /**
     * {@code GET  /voucher-statuses/:id} : get the "id" voucherStatus.
     *
     * @param id the id of the voucherStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the voucherStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/voucher-statuses/{id}")
    public ResponseEntity<VoucherStatusDTO> getVoucherStatus(@PathVariable Long id) {
        log.debug("REST request to get VoucherStatus : {}", id);
        Optional<VoucherStatusDTO> voucherStatusDTO = voucherStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(voucherStatusDTO);
    }

    /**
     * {@code DELETE  /voucher-statuses/:id} : delete the "id" voucherStatus.
     *
     * @param id the id of the voucherStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/voucher-statuses/{id}")
    public ResponseEntity<Void> deleteVoucherStatus(@PathVariable Long id) {
        log.debug("REST request to delete VoucherStatus : {}", id);
        voucherStatusService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
