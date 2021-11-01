package com.emclab.voucher.web.rest;

import com.emclab.voucher.repository.BenifitPackageRepository;
import com.emclab.voucher.service.BenifitPackageService;
import com.emclab.voucher.service.dto.BenifitPackageDTO;
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
 * REST controller for managing {@link com.emclab.voucher.domain.BenifitPackage}.
 */
@RestController
@RequestMapping("/api")
public class BenifitPackageResource {

    private final Logger log = LoggerFactory.getLogger(BenifitPackageResource.class);

    private static final String ENTITY_NAME = "benifitPackage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BenifitPackageService benifitPackageService;

    private final BenifitPackageRepository benifitPackageRepository;

    public BenifitPackageResource(BenifitPackageService benifitPackageService, BenifitPackageRepository benifitPackageRepository) {
        this.benifitPackageService = benifitPackageService;
        this.benifitPackageRepository = benifitPackageRepository;
    }

    /**
     * {@code POST  /benifit-packages} : Create a new benifitPackage.
     *
     * @param benifitPackageDTO the benifitPackageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new benifitPackageDTO, or with status {@code 400 (Bad Request)} if the benifitPackage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/benifit-packages")
    public ResponseEntity<BenifitPackageDTO> createBenifitPackage(@Valid @RequestBody BenifitPackageDTO benifitPackageDTO)
        throws URISyntaxException {
        log.debug("REST request to save BenifitPackage : {}", benifitPackageDTO);
        if (benifitPackageDTO.getId() != null) {
            throw new BadRequestAlertException("A new benifitPackage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BenifitPackageDTO result = benifitPackageService.save(benifitPackageDTO);
        return ResponseEntity
            .created(new URI("/api/benifit-packages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /benifit-packages/:id} : Updates an existing benifitPackage.
     *
     * @param id the id of the benifitPackageDTO to save.
     * @param benifitPackageDTO the benifitPackageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated benifitPackageDTO,
     * or with status {@code 400 (Bad Request)} if the benifitPackageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the benifitPackageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/benifit-packages/{id}")
    public ResponseEntity<BenifitPackageDTO> updateBenifitPackage(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BenifitPackageDTO benifitPackageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BenifitPackage : {}, {}", id, benifitPackageDTO);
        if (benifitPackageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, benifitPackageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!benifitPackageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BenifitPackageDTO result = benifitPackageService.save(benifitPackageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, benifitPackageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /benifit-packages/:id} : Partial updates given fields of an existing benifitPackage, field will ignore if it is null
     *
     * @param id the id of the benifitPackageDTO to save.
     * @param benifitPackageDTO the benifitPackageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated benifitPackageDTO,
     * or with status {@code 400 (Bad Request)} if the benifitPackageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the benifitPackageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the benifitPackageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/benifit-packages/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<BenifitPackageDTO> partialUpdateBenifitPackage(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BenifitPackageDTO benifitPackageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BenifitPackage partially : {}, {}", id, benifitPackageDTO);
        if (benifitPackageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, benifitPackageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!benifitPackageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BenifitPackageDTO> result = benifitPackageService.partialUpdate(benifitPackageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, benifitPackageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /benifit-packages} : get all the benifitPackages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of benifitPackages in body.
     */
    @GetMapping("/benifit-packages")
    public List<BenifitPackageDTO> getAllBenifitPackages() {
        log.debug("REST request to get all BenifitPackages");
        return benifitPackageService.findAll();
    }

    /**
     * {@code GET  /benifit-packages/:id} : get the "id" benifitPackage.
     *
     * @param id the id of the benifitPackageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the benifitPackageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/benifit-packages/{id}")
    public ResponseEntity<BenifitPackageDTO> getBenifitPackage(@PathVariable Long id) {
        log.debug("REST request to get BenifitPackage : {}", id);
        Optional<BenifitPackageDTO> benifitPackageDTO = benifitPackageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(benifitPackageDTO);
    }

    /**
     * {@code DELETE  /benifit-packages/:id} : delete the "id" benifitPackage.
     *
     * @param id the id of the benifitPackageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/benifit-packages/{id}")
    public ResponseEntity<Void> deleteBenifitPackage(@PathVariable Long id) {
        log.debug("REST request to delete BenifitPackage : {}", id);
        benifitPackageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
