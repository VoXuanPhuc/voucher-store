package com.emclab.voucher.web.rest;

import com.emclab.voucher.repository.VoucherImageRepository;
import com.emclab.voucher.service.VoucherImageService;
import com.emclab.voucher.service.dto.VoucherImageDTO;
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
 * REST controller for managing {@link com.emclab.voucher.domain.VoucherImage}.
 */
@RestController
@RequestMapping("/api")
public class VoucherImageResource {

    private final Logger log = LoggerFactory.getLogger(VoucherImageResource.class);

    private static final String ENTITY_NAME = "voucherImage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VoucherImageService voucherImageService;

    private final VoucherImageRepository voucherImageRepository;

    public VoucherImageResource(VoucherImageService voucherImageService, VoucherImageRepository voucherImageRepository) {
        this.voucherImageService = voucherImageService;
        this.voucherImageRepository = voucherImageRepository;
    }

    /**
     * {@code POST  /voucher-images} : Create a new voucherImage.
     *
     * @param voucherImageDTO the voucherImageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new voucherImageDTO, or with status {@code 400 (Bad Request)} if the voucherImage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/voucher-images")
    public ResponseEntity<VoucherImageDTO> createVoucherImage(@Valid @RequestBody VoucherImageDTO voucherImageDTO)
        throws URISyntaxException {
        log.debug("REST request to save VoucherImage : {}", voucherImageDTO);
        if (voucherImageDTO.getId() != null) {
            throw new BadRequestAlertException("A new voucherImage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VoucherImageDTO result = voucherImageService.save(voucherImageDTO);
        return ResponseEntity
            .created(new URI("/api/voucher-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /voucher-images/:id} : Updates an existing voucherImage.
     *
     * @param id the id of the voucherImageDTO to save.
     * @param voucherImageDTO the voucherImageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voucherImageDTO,
     * or with status {@code 400 (Bad Request)} if the voucherImageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the voucherImageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/voucher-images/{id}")
    public ResponseEntity<VoucherImageDTO> updateVoucherImage(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VoucherImageDTO voucherImageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VoucherImage : {}, {}", id, voucherImageDTO);
        if (voucherImageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voucherImageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voucherImageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VoucherImageDTO result = voucherImageService.save(voucherImageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, voucherImageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /voucher-images/:id} : Partial updates given fields of an existing voucherImage, field will ignore if it is null
     *
     * @param id the id of the voucherImageDTO to save.
     * @param voucherImageDTO the voucherImageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voucherImageDTO,
     * or with status {@code 400 (Bad Request)} if the voucherImageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the voucherImageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the voucherImageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/voucher-images/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<VoucherImageDTO> partialUpdateVoucherImage(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VoucherImageDTO voucherImageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VoucherImage partially : {}, {}", id, voucherImageDTO);
        if (voucherImageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voucherImageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voucherImageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VoucherImageDTO> result = voucherImageService.partialUpdate(voucherImageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, voucherImageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /voucher-images} : get all the voucherImages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of voucherImages in body.
     */
    @GetMapping("/voucher-images")
    public List<VoucherImageDTO> getAllVoucherImages() {
        log.debug("REST request to get all VoucherImages");
        return voucherImageService.findAll();
    }

    /**
     * {@code GET  /voucher-images/:id} : get the "id" voucherImage.
     *
     * @param id the id of the voucherImageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the voucherImageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/voucher-images/{id}")
    public ResponseEntity<VoucherImageDTO> getVoucherImage(@PathVariable Long id) {
        log.debug("REST request to get VoucherImage : {}", id);
        Optional<VoucherImageDTO> voucherImageDTO = voucherImageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(voucherImageDTO);
    }

    /**
     * {@code DELETE  /voucher-images/:id} : delete the "id" voucherImage.
     *
     * @param id the id of the voucherImageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/voucher-images/{id}")
    public ResponseEntity<Void> deleteVoucherImage(@PathVariable Long id) {
        log.debug("REST request to delete VoucherImage : {}", id);
        voucherImageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
