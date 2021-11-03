package com.emclab.voucher.web.rest;

import com.emclab.voucher.repository.MyOrderRepository;
import com.emclab.voucher.service.MyOrderService;
import com.emclab.voucher.service.dto.MyOrderDTO;
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
 * REST controller for managing {@link com.emclab.voucher.domain.MyOrder}.
 */
@RestController
@RequestMapping("/api")
public class MyOrderResource {

    private final Logger log = LoggerFactory.getLogger(MyOrderResource.class);

    private static final String ENTITY_NAME = "myOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MyOrderService myOrderService;

    private final MyOrderRepository myOrderRepository;

    public MyOrderResource(MyOrderService myOrderService, MyOrderRepository myOrderRepository) {
        this.myOrderService = myOrderService;
        this.myOrderRepository = myOrderRepository;
    }

    /**
     * {@code POST  /my-orders} : Create a new myOrder.
     *
     * @param myOrderDTO the myOrderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new myOrderDTO, or with status {@code 400 (Bad Request)} if the myOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/my-orders")
    public ResponseEntity<MyOrderDTO> createMyOrder(@Valid @RequestBody MyOrderDTO myOrderDTO) throws URISyntaxException {
        log.debug("REST request to save MyOrder : {}", myOrderDTO);
        if (myOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new myOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MyOrderDTO result = myOrderService.save(myOrderDTO);
        return ResponseEntity
            .created(new URI("/api/my-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /my-orders/:id} : Updates an existing myOrder.
     *
     * @param id the id of the myOrderDTO to save.
     * @param myOrderDTO the myOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated myOrderDTO,
     * or with status {@code 400 (Bad Request)} if the myOrderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the myOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/my-orders/{id}")
    public ResponseEntity<MyOrderDTO> updateMyOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MyOrderDTO myOrderDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MyOrder : {}, {}", id, myOrderDTO);
        if (myOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, myOrderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!myOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MyOrderDTO result = myOrderService.save(myOrderDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, myOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /my-orders/:id} : Partial updates given fields of an existing myOrder, field will ignore if it is null
     *
     * @param id the id of the myOrderDTO to save.
     * @param myOrderDTO the myOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated myOrderDTO,
     * or with status {@code 400 (Bad Request)} if the myOrderDTO is not valid,
     * or with status {@code 404 (Not Found)} if the myOrderDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the myOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/my-orders/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<MyOrderDTO> partialUpdateMyOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MyOrderDTO myOrderDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MyOrder partially : {}, {}", id, myOrderDTO);
        if (myOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, myOrderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!myOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MyOrderDTO> result = myOrderService.partialUpdate(myOrderDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, myOrderDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /my-orders} : get all the myOrders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of myOrders in body.
     */
    @GetMapping("/my-orders")
    public List<MyOrderDTO> getAllMyOrders() {
        log.debug("REST request to get all MyOrders");
        return myOrderService.findAll();
    }

    /**
     * {@code GET  /my-orders/:id} : get the "id" myOrder.
     *
     * @param id the id of the myOrderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the myOrderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/my-orders/{id}")
    public ResponseEntity<MyOrderDTO> getMyOrder(@PathVariable Long id) {
        log.debug("REST request to get MyOrder : {}", id);
        Optional<MyOrderDTO> myOrderDTO = myOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(myOrderDTO);
    }

    /**
     * {@code DELETE  /my-orders/:id} : delete the "id" myOrder.
     *
     * @param id the id of the myOrderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/my-orders/{id}")
    public ResponseEntity<Void> deleteMyOrder(@PathVariable Long id) {
        log.debug("REST request to delete MyOrder : {}", id);
        myOrderService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
