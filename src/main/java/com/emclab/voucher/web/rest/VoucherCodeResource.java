package com.emclab.voucher.web.rest;

import com.emclab.voucher.domain.VoucherStatus;
import com.emclab.voucher.repository.VoucherCodeRepository;
import com.emclab.voucher.service.VoucherCodeService;
import com.emclab.voucher.service.VoucherService;
import com.emclab.voucher.service.dto.VoucherCodeDTO;
import com.emclab.voucher.service.dto.VoucherDTO;
import com.emclab.voucher.service.mapper.VoucherMapper;
import com.emclab.voucher.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.emclab.voucher.domain.VoucherCode}.
 */
@RestController
@RequestMapping("/api")
public class VoucherCodeResource {

    private final Logger log = LoggerFactory.getLogger(VoucherCodeResource.class);

    private static final String ENTITY_NAME = "voucherCode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VoucherCodeService voucherCodeService;

    private final VoucherCodeRepository voucherCodeRepository;

    @Autowired
    VoucherService voucherService;

    @Autowired
    VoucherMapper voucherMapper;

    public VoucherCodeResource(VoucherCodeService voucherCodeService, VoucherCodeRepository voucherCodeRepository) {
        this.voucherCodeService = voucherCodeService;
        this.voucherCodeRepository = voucherCodeRepository;
    }

    /**
     * {@code POST  /voucher-codes} : Create a new voucherCode.
     *
     * @param voucherCodeDTO the voucherCodeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new voucherCodeDTO, or with status {@code 400 (Bad Request)} if the voucherCode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/voucher-codes")
    public ResponseEntity<VoucherCodeDTO> createVoucherCode(@Valid @RequestBody VoucherCodeDTO voucherCodeDTO) throws URISyntaxException {
        log.debug("REST request to save VoucherCode : {}", voucherCodeDTO);
        if (voucherCodeDTO.getId() != null) {
            throw new BadRequestAlertException("A new voucherCode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VoucherCodeDTO result = voucherCodeService.save(voucherCodeDTO);
        return ResponseEntity
            .created(new URI("/api/voucher-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /voucher-codes/:id} : Updates an existing voucherCode.
     *
     * @param id the id of the voucherCodeDTO to save.
     * @param voucherCodeDTO the voucherCodeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voucherCodeDTO,
     * or with status {@code 400 (Bad Request)} if the voucherCodeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the voucherCodeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/voucher-codes/{id}")
    public ResponseEntity<VoucherCodeDTO> updateVoucherCode(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VoucherCodeDTO voucherCodeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VoucherCode : {}, {}", id, voucherCodeDTO);
        if (voucherCodeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voucherCodeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voucherCodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VoucherCodeDTO result = voucherCodeService.save(voucherCodeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, voucherCodeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /voucher-codes/:id} : Partial updates given fields of an existing voucherCode, field will ignore if it is null
     *
     * @param id the id of the voucherCodeDTO to save.
     * @param voucherCodeDTO the voucherCodeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voucherCodeDTO,
     * or with status {@code 400 (Bad Request)} if the voucherCodeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the voucherCodeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the voucherCodeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/voucher-codes/{id}")
    public ResponseEntity<VoucherCodeDTO> partialUpdateVoucherCode(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VoucherCodeDTO voucherCodeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VoucherCode partially : {}, {}", id, voucherCodeDTO);
        if (voucherCodeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voucherCodeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voucherCodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VoucherCodeDTO> result = voucherCodeService.partialUpdate(voucherCodeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, voucherCodeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /voucher-codes} : get all the voucherCodes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of voucherCodes in body.
     */
    @GetMapping("/voucher-codes")
    public List<VoucherCodeDTO> getAllVoucherCodes() {
        log.debug("REST request to get all VoucherCodes");
        return voucherCodeService.findAll();
    }

    /**
     * {@code GET  /voucher-codes/:id} : get the "id" voucherCode.
     *
     * @param id the id of the voucherCodeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the voucherCodeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/voucher-codes/{id}")
    public ResponseEntity<VoucherCodeDTO> getVoucherCode(@PathVariable Long id) {
        log.debug("REST request to get VoucherCode : {}", id);
        Optional<VoucherCodeDTO> voucherCodeDTO = voucherCodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(voucherCodeDTO);
    }

    /**
     * {@code DELETE  /voucher-codes/:id} : delete the "id" voucherCode.
     *
     * @param id the id of the voucherCodeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/voucher-codes/{id}")
    public ResponseEntity<Void> deleteVoucherCode(@PathVariable Long id) {
        log.debug("REST request to delete VoucherCode : {}", id);
        voucherCodeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/voucher-codes/voucher/{id}")
    public Long countVoucherCode(@PathVariable Long id) {
        VoucherDTO voucherDTO = voucherService.findOne(id).get();
        VoucherStatus voucherStatus = new VoucherStatus();
        voucherStatus.setId(1l);
        voucherStatus.setName("available");
        return voucherCodeService.countVoucherCodeByVoucher(voucherMapper.toEntity(voucherDTO), voucherStatus);
    }

    @GetMapping("/voucher-codes/voucher-codes-of-currentuser")
    public ResponseEntity<Object> getVoucherCodeOfCurrentUser(@RequestParam Map<String, Object> param, Authentication authentication) {
        return ResponseEntity.ok(voucherCodeService.getVoucherCodeByOrderOfCurrentUser(param, authentication));
    }

    @GetMapping(path = "/voucher-codes", params = { "voucherId", "statusId", "limit" })
    public ResponseEntity<Object> getByStatusAndVoucher(@RequestParam Map<String, String> params) {
        int voucherId = Integer.parseInt(params.get("voucherId"));
        int statusId = Integer.parseInt(params.get("statusId"));
        int limit = Integer.parseInt(params.get("limit"));

        List<VoucherCodeDTO> voucherCodeDTOs = voucherCodeService.findByStatusIdAndVoucherIdAndLimit(statusId, voucherId, limit);

        return ResponseEntity.ok(voucherCodeDTOs);
    }
}
