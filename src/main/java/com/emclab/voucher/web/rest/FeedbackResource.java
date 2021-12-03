package com.emclab.voucher.web.rest;

import com.emclab.voucher.repository.FeedbackRepository;
import com.emclab.voucher.service.FeedbackService;
import com.emclab.voucher.service.dto.FeedbackDTO;
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
 * REST controller for managing {@link com.emclab.voucher.domain.Feedback}.
 */
@RestController
@RequestMapping("/api")
public class FeedbackResource {

    private final Logger log = LoggerFactory.getLogger(FeedbackResource.class);

    private static final String ENTITY_NAME = "feedback";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeedbackService feedbackService;

    private final FeedbackRepository feedbackRepository;

    public FeedbackResource(FeedbackService feedbackService, FeedbackRepository feedbackRepository) {
        this.feedbackService = feedbackService;
        this.feedbackRepository = feedbackRepository;
    }

    /**
     * {@code POST  /feedbacks} : Create a new feedback.
     *
     * @param feedbackDTO the feedbackDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feedbackDTO, or with status {@code 400 (Bad Request)} if the feedback has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/feedbacks")
    public ResponseEntity<FeedbackDTO> createFeedback(@Valid @RequestBody FeedbackDTO feedbackDTO) throws URISyntaxException {
        log.debug("REST request to save Feedback : {}", feedbackDTO);
        if (feedbackDTO.getId() != null) {
            throw new BadRequestAlertException("A new feedback cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeedbackDTO result = feedbackService.save(feedbackDTO);
        return ResponseEntity
            .created(new URI("/api/feedbacks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /feedbacks/:id} : Updates an existing feedback.
     *
     * @param id the id of the feedbackDTO to save.
     * @param feedbackDTO the feedbackDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedbackDTO,
     * or with status {@code 400 (Bad Request)} if the feedbackDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the feedbackDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/feedbacks/{id}")
    public ResponseEntity<FeedbackDTO> updateFeedback(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FeedbackDTO feedbackDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Feedback : {}, {}", id, feedbackDTO);
        if (feedbackDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feedbackDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feedbackRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FeedbackDTO result = feedbackService.save(feedbackDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, feedbackDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /feedbacks/:id} : Partial updates given fields of an existing feedback, field will ignore if it is null
     *
     * @param id the id of the feedbackDTO to save.
     * @param feedbackDTO the feedbackDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedbackDTO,
     * or with status {@code 400 (Bad Request)} if the feedbackDTO is not valid,
     * or with status {@code 404 (Not Found)} if the feedbackDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the feedbackDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/feedbacks/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FeedbackDTO> partialUpdateFeedback(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FeedbackDTO feedbackDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Feedback partially : {}, {}", id, feedbackDTO);
        if (feedbackDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feedbackDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feedbackRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FeedbackDTO> result = feedbackService.partialUpdate(feedbackDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, feedbackDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /feedbacks} : get all the feedbacks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of feedbacks in body.
     */
    @GetMapping("/feedbacks")
    public List<FeedbackDTO> getAllFeedbacks() {
        log.debug("REST request to get all Feedbacks");
        return feedbackService.findAll();
    }

    /**
     * {@code GET  /feedbacks/:id} : get the "id" feedback.
     *
     * @param id the id of the feedbackDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the feedbackDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/feedbacks/{id}")
    public ResponseEntity<FeedbackDTO> getFeedback(@PathVariable Long id) {
        log.debug("REST request to get Feedback : {}", id);
        Optional<FeedbackDTO> feedbackDTO = feedbackService.findOne(id);
        return ResponseUtil.wrapOrNotFound(feedbackDTO);
    }

    /**
     * {@code DELETE  /feedbacks/:id} : delete the "id" feedback.
     *
     * @param id the id of the feedbackDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/feedbacks/count-feedback-by-voucher/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        log.debug("REST request to delete Feedback : {}", id);
        feedbackService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/feedbacks-voucher/{idVoucher}")
    public List<FeedbackDTO> getFeedbyVoucher(
        @PathVariable Long idVoucher,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "0") int rate
    ) {
        List<FeedbackDTO> feedbacksResult = feedbackService.findByVoucher(idVoucher, rate, page);
        return feedbacksResult;
    }

    @GetMapping("/feedbacks/count-feedback-by-voucher/{id}")
    public Long countFeedbackbyVoucher(@PathVariable Long id) {
        return feedbackService.countByVoucher(id);
    }

    @GetMapping("/feedbacks/count-feedback-by-voucher/{id}/{rate}")
    public Long countFeedbackbyVoucherAndRate(@PathVariable Long id, @PathVariable int rate) {
        return feedbackService.countByVoucherAndRate(id, rate);
    }
    //    @GetMapping("/feedbacks/feedbacks-voucher")
    //    public List<FeedbackDTO> getFeedbackByVoucherAndRate(@RequestParam(defaultValue = "5") int rate) {
    //        List<FeedbackDTO> feedbacksResult = feedbackService.findByVoucherAndRate(rate);
    //        return feedbacksResult;
    //    }
}
