package com.emclab.voucher.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.emclab.voucher.IntegrationTest;
import com.emclab.voucher.domain.VoucherImage;
import com.emclab.voucher.repository.VoucherImageRepository;
import com.emclab.voucher.service.dto.VoucherImageDTO;
import com.emclab.voucher.service.mapper.VoucherImageMapper;
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
 * Integration tests for the {@link VoucherImageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VoucherImageResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/voucher-images";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VoucherImageRepository voucherImageRepository;

    @Autowired
    private VoucherImageMapper voucherImageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVoucherImageMockMvc;

    private VoucherImage voucherImage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VoucherImage createEntity(EntityManager em) {
        VoucherImage voucherImage = new VoucherImage().name(DEFAULT_NAME);
        return voucherImage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VoucherImage createUpdatedEntity(EntityManager em) {
        VoucherImage voucherImage = new VoucherImage().name(UPDATED_NAME);
        return voucherImage;
    }

    @BeforeEach
    public void initTest() {
        voucherImage = createEntity(em);
    }

    @Test
    @Transactional
    void createVoucherImage() throws Exception {
        int databaseSizeBeforeCreate = voucherImageRepository.findAll().size();
        // Create the VoucherImage
        VoucherImageDTO voucherImageDTO = voucherImageMapper.toDto(voucherImage);
        restVoucherImageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voucherImageDTO))
            )
            .andExpect(status().isCreated());

        // Validate the VoucherImage in the database
        List<VoucherImage> voucherImageList = voucherImageRepository.findAll();
        assertThat(voucherImageList).hasSize(databaseSizeBeforeCreate + 1);
        VoucherImage testVoucherImage = voucherImageList.get(voucherImageList.size() - 1);
        assertThat(testVoucherImage.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createVoucherImageWithExistingId() throws Exception {
        // Create the VoucherImage with an existing ID
        voucherImage.setId(1L);
        VoucherImageDTO voucherImageDTO = voucherImageMapper.toDto(voucherImage);

        int databaseSizeBeforeCreate = voucherImageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoucherImageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voucherImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoucherImage in the database
        List<VoucherImage> voucherImageList = voucherImageRepository.findAll();
        assertThat(voucherImageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = voucherImageRepository.findAll().size();
        // set the field null
        voucherImage.setName(null);

        // Create the VoucherImage, which fails.
        VoucherImageDTO voucherImageDTO = voucherImageMapper.toDto(voucherImage);

        restVoucherImageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voucherImageDTO))
            )
            .andExpect(status().isBadRequest());

        List<VoucherImage> voucherImageList = voucherImageRepository.findAll();
        assertThat(voucherImageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVoucherImages() throws Exception {
        // Initialize the database
        voucherImageRepository.saveAndFlush(voucherImage);

        // Get all the voucherImageList
        restVoucherImageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucherImage.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getVoucherImage() throws Exception {
        // Initialize the database
        voucherImageRepository.saveAndFlush(voucherImage);

        // Get the voucherImage
        restVoucherImageMockMvc
            .perform(get(ENTITY_API_URL_ID, voucherImage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(voucherImage.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingVoucherImage() throws Exception {
        // Get the voucherImage
        restVoucherImageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVoucherImage() throws Exception {
        // Initialize the database
        voucherImageRepository.saveAndFlush(voucherImage);

        int databaseSizeBeforeUpdate = voucherImageRepository.findAll().size();

        // Update the voucherImage
        VoucherImage updatedVoucherImage = voucherImageRepository.findById(voucherImage.getId()).get();
        // Disconnect from session so that the updates on updatedVoucherImage are not directly saved in db
        em.detach(updatedVoucherImage);
        updatedVoucherImage.name(UPDATED_NAME);
        VoucherImageDTO voucherImageDTO = voucherImageMapper.toDto(updatedVoucherImage);

        restVoucherImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, voucherImageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voucherImageDTO))
            )
            .andExpect(status().isOk());

        // Validate the VoucherImage in the database
        List<VoucherImage> voucherImageList = voucherImageRepository.findAll();
        assertThat(voucherImageList).hasSize(databaseSizeBeforeUpdate);
        VoucherImage testVoucherImage = voucherImageList.get(voucherImageList.size() - 1);
        assertThat(testVoucherImage.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingVoucherImage() throws Exception {
        int databaseSizeBeforeUpdate = voucherImageRepository.findAll().size();
        voucherImage.setId(count.incrementAndGet());

        // Create the VoucherImage
        VoucherImageDTO voucherImageDTO = voucherImageMapper.toDto(voucherImage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoucherImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, voucherImageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voucherImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoucherImage in the database
        List<VoucherImage> voucherImageList = voucherImageRepository.findAll();
        assertThat(voucherImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVoucherImage() throws Exception {
        int databaseSizeBeforeUpdate = voucherImageRepository.findAll().size();
        voucherImage.setId(count.incrementAndGet());

        // Create the VoucherImage
        VoucherImageDTO voucherImageDTO = voucherImageMapper.toDto(voucherImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voucherImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoucherImage in the database
        List<VoucherImage> voucherImageList = voucherImageRepository.findAll();
        assertThat(voucherImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVoucherImage() throws Exception {
        int databaseSizeBeforeUpdate = voucherImageRepository.findAll().size();
        voucherImage.setId(count.incrementAndGet());

        // Create the VoucherImage
        VoucherImageDTO voucherImageDTO = voucherImageMapper.toDto(voucherImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherImageMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voucherImageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VoucherImage in the database
        List<VoucherImage> voucherImageList = voucherImageRepository.findAll();
        assertThat(voucherImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVoucherImageWithPatch() throws Exception {
        // Initialize the database
        voucherImageRepository.saveAndFlush(voucherImage);

        int databaseSizeBeforeUpdate = voucherImageRepository.findAll().size();

        // Update the voucherImage using partial update
        VoucherImage partialUpdatedVoucherImage = new VoucherImage();
        partialUpdatedVoucherImage.setId(voucherImage.getId());

        partialUpdatedVoucherImage.name(UPDATED_NAME);

        restVoucherImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoucherImage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVoucherImage))
            )
            .andExpect(status().isOk());

        // Validate the VoucherImage in the database
        List<VoucherImage> voucherImageList = voucherImageRepository.findAll();
        assertThat(voucherImageList).hasSize(databaseSizeBeforeUpdate);
        VoucherImage testVoucherImage = voucherImageList.get(voucherImageList.size() - 1);
        assertThat(testVoucherImage.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateVoucherImageWithPatch() throws Exception {
        // Initialize the database
        voucherImageRepository.saveAndFlush(voucherImage);

        int databaseSizeBeforeUpdate = voucherImageRepository.findAll().size();

        // Update the voucherImage using partial update
        VoucherImage partialUpdatedVoucherImage = new VoucherImage();
        partialUpdatedVoucherImage.setId(voucherImage.getId());

        partialUpdatedVoucherImage.name(UPDATED_NAME);

        restVoucherImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoucherImage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVoucherImage))
            )
            .andExpect(status().isOk());

        // Validate the VoucherImage in the database
        List<VoucherImage> voucherImageList = voucherImageRepository.findAll();
        assertThat(voucherImageList).hasSize(databaseSizeBeforeUpdate);
        VoucherImage testVoucherImage = voucherImageList.get(voucherImageList.size() - 1);
        assertThat(testVoucherImage.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingVoucherImage() throws Exception {
        int databaseSizeBeforeUpdate = voucherImageRepository.findAll().size();
        voucherImage.setId(count.incrementAndGet());

        // Create the VoucherImage
        VoucherImageDTO voucherImageDTO = voucherImageMapper.toDto(voucherImage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoucherImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, voucherImageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(voucherImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoucherImage in the database
        List<VoucherImage> voucherImageList = voucherImageRepository.findAll();
        assertThat(voucherImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVoucherImage() throws Exception {
        int databaseSizeBeforeUpdate = voucherImageRepository.findAll().size();
        voucherImage.setId(count.incrementAndGet());

        // Create the VoucherImage
        VoucherImageDTO voucherImageDTO = voucherImageMapper.toDto(voucherImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(voucherImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoucherImage in the database
        List<VoucherImage> voucherImageList = voucherImageRepository.findAll();
        assertThat(voucherImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVoucherImage() throws Exception {
        int databaseSizeBeforeUpdate = voucherImageRepository.findAll().size();
        voucherImage.setId(count.incrementAndGet());

        // Create the VoucherImage
        VoucherImageDTO voucherImageDTO = voucherImageMapper.toDto(voucherImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherImageMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(voucherImageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VoucherImage in the database
        List<VoucherImage> voucherImageList = voucherImageRepository.findAll();
        assertThat(voucherImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVoucherImage() throws Exception {
        // Initialize the database
        voucherImageRepository.saveAndFlush(voucherImage);

        int databaseSizeBeforeDelete = voucherImageRepository.findAll().size();

        // Delete the voucherImage
        restVoucherImageMockMvc
            .perform(delete(ENTITY_API_URL_ID, voucherImage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VoucherImage> voucherImageList = voucherImageRepository.findAll();
        assertThat(voucherImageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
