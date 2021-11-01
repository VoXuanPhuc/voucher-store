package com.emclab.voucher.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.emclab.voucher.IntegrationTest;
import com.emclab.voucher.domain.Gift;
import com.emclab.voucher.repository.GiftRepository;
import com.emclab.voucher.service.dto.GiftDTO;
import com.emclab.voucher.service.mapper.GiftMapper;
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
 * Integration tests for the {@link GiftResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GiftResourceIT {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/gifts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GiftRepository giftRepository;

    @Autowired
    private GiftMapper giftMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGiftMockMvc;

    private Gift gift;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gift createEntity(EntityManager em) {
        Gift gift = new Gift().message(DEFAULT_MESSAGE);
        return gift;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gift createUpdatedEntity(EntityManager em) {
        Gift gift = new Gift().message(UPDATED_MESSAGE);
        return gift;
    }

    @BeforeEach
    public void initTest() {
        gift = createEntity(em);
    }

    @Test
    @Transactional
    void createGift() throws Exception {
        int databaseSizeBeforeCreate = giftRepository.findAll().size();
        // Create the Gift
        GiftDTO giftDTO = giftMapper.toDto(gift);
        restGiftMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(giftDTO)))
            .andExpect(status().isCreated());

        // Validate the Gift in the database
        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeCreate + 1);
        Gift testGift = giftList.get(giftList.size() - 1);
        assertThat(testGift.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    void createGiftWithExistingId() throws Exception {
        // Create the Gift with an existing ID
        gift.setId(1L);
        GiftDTO giftDTO = giftMapper.toDto(gift);

        int databaseSizeBeforeCreate = giftRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGiftMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(giftDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Gift in the database
        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGifts() throws Exception {
        // Initialize the database
        giftRepository.saveAndFlush(gift);

        // Get all the giftList
        restGiftMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gift.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)));
    }

    @Test
    @Transactional
    void getGift() throws Exception {
        // Initialize the database
        giftRepository.saveAndFlush(gift);

        // Get the gift
        restGiftMockMvc
            .perform(get(ENTITY_API_URL_ID, gift.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gift.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE));
    }

    @Test
    @Transactional
    void getNonExistingGift() throws Exception {
        // Get the gift
        restGiftMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGift() throws Exception {
        // Initialize the database
        giftRepository.saveAndFlush(gift);

        int databaseSizeBeforeUpdate = giftRepository.findAll().size();

        // Update the gift
        Gift updatedGift = giftRepository.findById(gift.getId()).get();
        // Disconnect from session so that the updates on updatedGift are not directly saved in db
        em.detach(updatedGift);
        updatedGift.message(UPDATED_MESSAGE);
        GiftDTO giftDTO = giftMapper.toDto(updatedGift);

        restGiftMockMvc
            .perform(
                put(ENTITY_API_URL_ID, giftDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(giftDTO))
            )
            .andExpect(status().isOk());

        // Validate the Gift in the database
        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeUpdate);
        Gift testGift = giftList.get(giftList.size() - 1);
        assertThat(testGift.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void putNonExistingGift() throws Exception {
        int databaseSizeBeforeUpdate = giftRepository.findAll().size();
        gift.setId(count.incrementAndGet());

        // Create the Gift
        GiftDTO giftDTO = giftMapper.toDto(gift);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGiftMockMvc
            .perform(
                put(ENTITY_API_URL_ID, giftDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(giftDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gift in the database
        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGift() throws Exception {
        int databaseSizeBeforeUpdate = giftRepository.findAll().size();
        gift.setId(count.incrementAndGet());

        // Create the Gift
        GiftDTO giftDTO = giftMapper.toDto(gift);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGiftMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(giftDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gift in the database
        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGift() throws Exception {
        int databaseSizeBeforeUpdate = giftRepository.findAll().size();
        gift.setId(count.incrementAndGet());

        // Create the Gift
        GiftDTO giftDTO = giftMapper.toDto(gift);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGiftMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(giftDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gift in the database
        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGiftWithPatch() throws Exception {
        // Initialize the database
        giftRepository.saveAndFlush(gift);

        int databaseSizeBeforeUpdate = giftRepository.findAll().size();

        // Update the gift using partial update
        Gift partialUpdatedGift = new Gift();
        partialUpdatedGift.setId(gift.getId());

        partialUpdatedGift.message(UPDATED_MESSAGE);

        restGiftMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGift.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGift))
            )
            .andExpect(status().isOk());

        // Validate the Gift in the database
        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeUpdate);
        Gift testGift = giftList.get(giftList.size() - 1);
        assertThat(testGift.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void fullUpdateGiftWithPatch() throws Exception {
        // Initialize the database
        giftRepository.saveAndFlush(gift);

        int databaseSizeBeforeUpdate = giftRepository.findAll().size();

        // Update the gift using partial update
        Gift partialUpdatedGift = new Gift();
        partialUpdatedGift.setId(gift.getId());

        partialUpdatedGift.message(UPDATED_MESSAGE);

        restGiftMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGift.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGift))
            )
            .andExpect(status().isOk());

        // Validate the Gift in the database
        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeUpdate);
        Gift testGift = giftList.get(giftList.size() - 1);
        assertThat(testGift.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void patchNonExistingGift() throws Exception {
        int databaseSizeBeforeUpdate = giftRepository.findAll().size();
        gift.setId(count.incrementAndGet());

        // Create the Gift
        GiftDTO giftDTO = giftMapper.toDto(gift);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGiftMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, giftDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(giftDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gift in the database
        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGift() throws Exception {
        int databaseSizeBeforeUpdate = giftRepository.findAll().size();
        gift.setId(count.incrementAndGet());

        // Create the Gift
        GiftDTO giftDTO = giftMapper.toDto(gift);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGiftMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(giftDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gift in the database
        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGift() throws Exception {
        int databaseSizeBeforeUpdate = giftRepository.findAll().size();
        gift.setId(count.incrementAndGet());

        // Create the Gift
        GiftDTO giftDTO = giftMapper.toDto(gift);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGiftMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(giftDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gift in the database
        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGift() throws Exception {
        // Initialize the database
        giftRepository.saveAndFlush(gift);

        int databaseSizeBeforeDelete = giftRepository.findAll().size();

        // Delete the gift
        restGiftMockMvc
            .perform(delete(ENTITY_API_URL_ID, gift.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
