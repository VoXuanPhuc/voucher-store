package com.emclab.voucher.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.emclab.voucher.IntegrationTest;
import com.emclab.voucher.domain.FeedbackImage;
import com.emclab.voucher.repository.FeedbackImageRepository;
import com.emclab.voucher.service.dto.FeedbackImageDTO;
import com.emclab.voucher.service.mapper.FeedbackImageMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FeedbackImageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FeedbackImageResourceIT {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/feedback-images";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FeedbackImageRepository feedbackImageRepository;

    @Autowired
    private FeedbackImageMapper feedbackImageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeedbackImageMockMvc;

    private FeedbackImage feedbackImage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeedbackImage createEntity(EntityManager em) {
        FeedbackImage feedbackImage = new FeedbackImage().content(DEFAULT_CONTENT);
        return feedbackImage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeedbackImage createUpdatedEntity(EntityManager em) {
        FeedbackImage feedbackImage = new FeedbackImage().content(UPDATED_CONTENT);
        return feedbackImage;
    }

    @BeforeEach
    public void initTest() {
        feedbackImage = createEntity(em);
    }

    @Test
    @Transactional
    void createFeedbackImage() throws Exception {
        int databaseSizeBeforeCreate = feedbackImageRepository.findAll().size();
        // Create the FeedbackImage
        FeedbackImageDTO feedbackImageDTO = feedbackImageMapper.toDto(feedbackImage);
        restFeedbackImageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feedbackImageDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FeedbackImage in the database
        List<FeedbackImage> feedbackImageList = feedbackImageRepository.findAll();
        assertThat(feedbackImageList).hasSize(databaseSizeBeforeCreate + 1);
        FeedbackImage testFeedbackImage = feedbackImageList.get(feedbackImageList.size() - 1);
        assertThat(testFeedbackImage.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    void createFeedbackImageWithExistingId() throws Exception {
        // Create the FeedbackImage with an existing ID
        feedbackImage.setId(1L);
        FeedbackImageDTO feedbackImageDTO = feedbackImageMapper.toDto(feedbackImage);

        int databaseSizeBeforeCreate = feedbackImageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeedbackImageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feedbackImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackImage in the database
        List<FeedbackImage> feedbackImageList = feedbackImageRepository.findAll();
        assertThat(feedbackImageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackImageRepository.findAll().size();
        // set the field null
        feedbackImage.setContent(null);

        // Create the FeedbackImage, which fails.
        FeedbackImageDTO feedbackImageDTO = feedbackImageMapper.toDto(feedbackImage);

        restFeedbackImageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feedbackImageDTO))
            )
            .andExpect(status().isBadRequest());

        List<FeedbackImage> feedbackImageList = feedbackImageRepository.findAll();
        assertThat(feedbackImageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFeedbackImages() throws Exception {
        // Initialize the database
        feedbackImageRepository.saveAndFlush(feedbackImage);

        // Get all the feedbackImageList
        restFeedbackImageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedbackImage.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }

    @Test
    @Transactional
    void getFeedbackImage() throws Exception {
        // Initialize the database
        feedbackImageRepository.saveAndFlush(feedbackImage);

        // Get the feedbackImage
        restFeedbackImageMockMvc
            .perform(get(ENTITY_API_URL_ID, feedbackImage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(feedbackImage.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    void getNonExistingFeedbackImage() throws Exception {
        // Get the feedbackImage
        restFeedbackImageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFeedbackImage() throws Exception {
        // Initialize the database
        feedbackImageRepository.saveAndFlush(feedbackImage);

        int databaseSizeBeforeUpdate = feedbackImageRepository.findAll().size();

        // Update the feedbackImage
        FeedbackImage updatedFeedbackImage = feedbackImageRepository.findById(feedbackImage.getId()).get();
        // Disconnect from session so that the updates on updatedFeedbackImage are not directly saved in db
        em.detach(updatedFeedbackImage);
        updatedFeedbackImage.content(UPDATED_CONTENT);
        FeedbackImageDTO feedbackImageDTO = feedbackImageMapper.toDto(updatedFeedbackImage);

        restFeedbackImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, feedbackImageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackImageDTO))
            )
            .andExpect(status().isOk());

        // Validate the FeedbackImage in the database
        List<FeedbackImage> feedbackImageList = feedbackImageRepository.findAll();
        assertThat(feedbackImageList).hasSize(databaseSizeBeforeUpdate);
        FeedbackImage testFeedbackImage = feedbackImageList.get(feedbackImageList.size() - 1);
        assertThat(testFeedbackImage.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void putNonExistingFeedbackImage() throws Exception {
        int databaseSizeBeforeUpdate = feedbackImageRepository.findAll().size();
        feedbackImage.setId(count.incrementAndGet());

        // Create the FeedbackImage
        FeedbackImageDTO feedbackImageDTO = feedbackImageMapper.toDto(feedbackImage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedbackImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, feedbackImageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackImage in the database
        List<FeedbackImage> feedbackImageList = feedbackImageRepository.findAll();
        assertThat(feedbackImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFeedbackImage() throws Exception {
        int databaseSizeBeforeUpdate = feedbackImageRepository.findAll().size();
        feedbackImage.setId(count.incrementAndGet());

        // Create the FeedbackImage
        FeedbackImageDTO feedbackImageDTO = feedbackImageMapper.toDto(feedbackImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedbackImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackImage in the database
        List<FeedbackImage> feedbackImageList = feedbackImageRepository.findAll();
        assertThat(feedbackImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFeedbackImage() throws Exception {
        int databaseSizeBeforeUpdate = feedbackImageRepository.findAll().size();
        feedbackImage.setId(count.incrementAndGet());

        // Create the FeedbackImage
        FeedbackImageDTO feedbackImageDTO = feedbackImageMapper.toDto(feedbackImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedbackImageMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feedbackImageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FeedbackImage in the database
        List<FeedbackImage> feedbackImageList = feedbackImageRepository.findAll();
        assertThat(feedbackImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFeedbackImageWithPatch() throws Exception {
        // Initialize the database
        feedbackImageRepository.saveAndFlush(feedbackImage);

        int databaseSizeBeforeUpdate = feedbackImageRepository.findAll().size();

        // Update the feedbackImage using partial update
        FeedbackImage partialUpdatedFeedbackImage = new FeedbackImage();
        partialUpdatedFeedbackImage.setId(feedbackImage.getId());

        partialUpdatedFeedbackImage.content(UPDATED_CONTENT);

        restFeedbackImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeedbackImage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeedbackImage))
            )
            .andExpect(status().isOk());

        // Validate the FeedbackImage in the database
        List<FeedbackImage> feedbackImageList = feedbackImageRepository.findAll();
        assertThat(feedbackImageList).hasSize(databaseSizeBeforeUpdate);
        FeedbackImage testFeedbackImage = feedbackImageList.get(feedbackImageList.size() - 1);
        assertThat(testFeedbackImage.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void fullUpdateFeedbackImageWithPatch() throws Exception {
        // Initialize the database
        feedbackImageRepository.saveAndFlush(feedbackImage);

        int databaseSizeBeforeUpdate = feedbackImageRepository.findAll().size();

        // Update the feedbackImage using partial update
        FeedbackImage partialUpdatedFeedbackImage = new FeedbackImage();
        partialUpdatedFeedbackImage.setId(feedbackImage.getId());

        partialUpdatedFeedbackImage.content(UPDATED_CONTENT);

        restFeedbackImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeedbackImage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeedbackImage))
            )
            .andExpect(status().isOk());

        // Validate the FeedbackImage in the database
        List<FeedbackImage> feedbackImageList = feedbackImageRepository.findAll();
        assertThat(feedbackImageList).hasSize(databaseSizeBeforeUpdate);
        FeedbackImage testFeedbackImage = feedbackImageList.get(feedbackImageList.size() - 1);
        assertThat(testFeedbackImage.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void patchNonExistingFeedbackImage() throws Exception {
        int databaseSizeBeforeUpdate = feedbackImageRepository.findAll().size();
        feedbackImage.setId(count.incrementAndGet());

        // Create the FeedbackImage
        FeedbackImageDTO feedbackImageDTO = feedbackImageMapper.toDto(feedbackImage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedbackImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, feedbackImageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feedbackImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackImage in the database
        List<FeedbackImage> feedbackImageList = feedbackImageRepository.findAll();
        assertThat(feedbackImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFeedbackImage() throws Exception {
        int databaseSizeBeforeUpdate = feedbackImageRepository.findAll().size();
        feedbackImage.setId(count.incrementAndGet());

        // Create the FeedbackImage
        FeedbackImageDTO feedbackImageDTO = feedbackImageMapper.toDto(feedbackImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedbackImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feedbackImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackImage in the database
        List<FeedbackImage> feedbackImageList = feedbackImageRepository.findAll();
        assertThat(feedbackImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFeedbackImage() throws Exception {
        int databaseSizeBeforeUpdate = feedbackImageRepository.findAll().size();
        feedbackImage.setId(count.incrementAndGet());

        // Create the FeedbackImage
        FeedbackImageDTO feedbackImageDTO = feedbackImageMapper.toDto(feedbackImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedbackImageMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feedbackImageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FeedbackImage in the database
        List<FeedbackImage> feedbackImageList = feedbackImageRepository.findAll();
        assertThat(feedbackImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFeedbackImage() throws Exception {
        // Initialize the database
        feedbackImageRepository.saveAndFlush(feedbackImage);

        int databaseSizeBeforeDelete = feedbackImageRepository.findAll().size();

        // Delete the feedbackImage
        restFeedbackImageMockMvc
            .perform(delete(ENTITY_API_URL_ID, feedbackImage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FeedbackImage> feedbackImageList = feedbackImageRepository.findAll();
        assertThat(feedbackImageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
