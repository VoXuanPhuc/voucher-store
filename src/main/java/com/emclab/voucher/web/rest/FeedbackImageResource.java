package com.emclab.voucher.web.rest;

import com.emclab.voucher.repository.FeedbackImageRepository;
import com.emclab.voucher.service.FeedbackImageService;
import com.emclab.voucher.service.dto.FeedbackImageDTO;
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
 * REST controller for managing {@link com.emclab.voucher.domain.FeedbackImage}.
 */
@RestController
@RequestMapping("/api")
public class FeedbackImageResource {

    private final Logger log = LoggerFactory.getLogger(FeedbackImageResource.class);

    private static final String ENTITY_NAME = "feedbackImage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeedbackImageService feedbackImageService;

    private final FeedbackImageRepository feedbackImageRepository;

    public FeedbackImageResource(FeedbackImageService feedbackImageService, FeedbackImageRepository feedbackImageRepository) {
        this.feedbackImageService = feedbackImageService;
        this.feedbackImageRepository = feedbackImageRepository;
    }

    /**
     * {@code POST  /feedback-images} : Create a new feedbackImage.
     *
     * @param feedbackImageDTO the feedbackImageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feedbackImageDTO, or with status {@code 400 (Bad Request)} if the feedbackImage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/feedback-images")
    public ResponseEntity<FeedbackImageDTO> createFeedbackImage(@Valid @RequestBody FeedbackImageDTO feedbackImageDTO)
        throws URISyntaxException {
        log.debug("REST request to save FeedbackImage : {}", feedbackImageDTO);
        if (feedbackImageDTO.getId() != null) {
            throw new BadRequestAlertException("A new feedbackImage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeedbackImageDTO result = feedbackImageService.save(feedbackImageDTO);
        return ResponseEntity
            .created(new URI("/api/feedback-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /feedback-images/:id} : Updates an existing feedbackImage.
     *
     * @param id the id of the feedbackImageDTO to save.
     * @param feedbackImageDTO the feedbackImageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedbackImageDTO,
     * or with status {@code 400 (Bad Request)} if the feedbackImageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the feedbackImageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/feedback-images/{id}")
    public ResponseEntity<FeedbackImageDTO> updateFeedbackImage(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FeedbackImageDTO feedbackImageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FeedbackImage : {}, {}", id, feedbackImageDTO);
        if (feedbackImageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feedbackImageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feedbackImageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FeedbackImageDTO result = feedbackImageService.save(feedbackImageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, feedbackImageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /feedback-images/:id} : Partial updates given fields of an existing feedbackImage, field will ignore if it is null
     *
     * @param id the id of the feedbackImageDTO to save.
     * @param feedbackImageDTO the feedbackImageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedbackImageDTO,
     * or with status {@code 400 (Bad Request)} if the feedbackImageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the feedbackImageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the feedbackImageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/feedback-images/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FeedbackImageDTO> partialUpdateFeedbackImage(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FeedbackImageDTO feedbackImageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FeedbackImage partially : {}, {}", id, feedbackImageDTO);
        if (feedbackImageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feedbackImageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feedbackImageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FeedbackImageDTO> result = feedbackImageService.partialUpdate(feedbackImageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, feedbackImageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /feedback-images} : get all the feedbackImages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of feedbackImages in body.
     */
    @GetMapping("/feedback-images")
    public List<FeedbackImageDTO> getAllFeedbackImages() {
        log.debug("REST request to get all FeedbackImages");
        return feedbackImageService.findAll();
    }

    /**
     * {@code GET  /feedback-images/:id} : get the "id" feedbackImage.
     *
     * @param id the id of the feedbackImageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the feedbackImageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/feedback-images/{id}")
    public ResponseEntity<FeedbackImageDTO> getFeedbackImage(@PathVariable Long id) {
        log.debug("REST request to get FeedbackImage : {}", id);
        Optional<FeedbackImageDTO> feedbackImageDTO = feedbackImageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(feedbackImageDTO);
    }

    /**
     * {@code DELETE  /feedback-images/:id} : delete the "id" feedbackImage.
     *
     * @param id the id of the feedbackImageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/feedback-images/{id}")
    public ResponseEntity<Void> deleteFeedbackImage(@PathVariable Long id) {
        log.debug("REST request to delete FeedbackImage : {}", id);
        feedbackImageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
