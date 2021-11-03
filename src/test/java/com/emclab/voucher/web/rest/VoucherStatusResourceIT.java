package com.emclab.voucher.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.emclab.voucher.IntegrationTest;
import com.emclab.voucher.domain.VoucherStatus;
import com.emclab.voucher.repository.VoucherStatusRepository;
import com.emclab.voucher.service.dto.VoucherStatusDTO;
import com.emclab.voucher.service.mapper.VoucherStatusMapper;
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
 * Integration tests for the {@link VoucherStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VoucherStatusResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/voucher-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VoucherStatusRepository voucherStatusRepository;

    @Autowired
    private VoucherStatusMapper voucherStatusMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVoucherStatusMockMvc;

    private VoucherStatus voucherStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VoucherStatus createEntity(EntityManager em) {
        VoucherStatus voucherStatus = new VoucherStatus().name(DEFAULT_NAME);
        return voucherStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VoucherStatus createUpdatedEntity(EntityManager em) {
        VoucherStatus voucherStatus = new VoucherStatus().name(UPDATED_NAME);
        return voucherStatus;
    }

    @BeforeEach
    public void initTest() {
        voucherStatus = createEntity(em);
    }

    @Test
    @Transactional
    void createVoucherStatus() throws Exception {
        int databaseSizeBeforeCreate = voucherStatusRepository.findAll().size();
        // Create the VoucherStatus
        VoucherStatusDTO voucherStatusDTO = voucherStatusMapper.toDto(voucherStatus);
        restVoucherStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voucherStatusDTO))
            )
            .andExpect(status().isCreated());

        // Validate the VoucherStatus in the database
        List<VoucherStatus> voucherStatusList = voucherStatusRepository.findAll();
        assertThat(voucherStatusList).hasSize(databaseSizeBeforeCreate + 1);
        VoucherStatus testVoucherStatus = voucherStatusList.get(voucherStatusList.size() - 1);
        assertThat(testVoucherStatus.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createVoucherStatusWithExistingId() throws Exception {
        // Create the VoucherStatus with an existing ID
        voucherStatus.setId(1L);
        VoucherStatusDTO voucherStatusDTO = voucherStatusMapper.toDto(voucherStatus);

        int databaseSizeBeforeCreate = voucherStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoucherStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voucherStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoucherStatus in the database
        List<VoucherStatus> voucherStatusList = voucherStatusRepository.findAll();
        assertThat(voucherStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = voucherStatusRepository.findAll().size();
        // set the field null
        voucherStatus.setName(null);

        // Create the VoucherStatus, which fails.
        VoucherStatusDTO voucherStatusDTO = voucherStatusMapper.toDto(voucherStatus);

        restVoucherStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voucherStatusDTO))
            )
            .andExpect(status().isBadRequest());

        List<VoucherStatus> voucherStatusList = voucherStatusRepository.findAll();
        assertThat(voucherStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVoucherStatuses() throws Exception {
        // Initialize the database
        voucherStatusRepository.saveAndFlush(voucherStatus);

        // Get all the voucherStatusList
        restVoucherStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucherStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getVoucherStatus() throws Exception {
        // Initialize the database
        voucherStatusRepository.saveAndFlush(voucherStatus);

        // Get the voucherStatus
        restVoucherStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, voucherStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(voucherStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingVoucherStatus() throws Exception {
        // Get the voucherStatus
        restVoucherStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVoucherStatus() throws Exception {
        // Initialize the database
        voucherStatusRepository.saveAndFlush(voucherStatus);

        int databaseSizeBeforeUpdate = voucherStatusRepository.findAll().size();

        // Update the voucherStatus
        VoucherStatus updatedVoucherStatus = voucherStatusRepository.findById(voucherStatus.getId()).get();
        // Disconnect from session so that the updates on updatedVoucherStatus are not directly saved in db
        em.detach(updatedVoucherStatus);
        updatedVoucherStatus.name(UPDATED_NAME);
        VoucherStatusDTO voucherStatusDTO = voucherStatusMapper.toDto(updatedVoucherStatus);

        restVoucherStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, voucherStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voucherStatusDTO))
            )
            .andExpect(status().isOk());

        // Validate the VoucherStatus in the database
        List<VoucherStatus> voucherStatusList = voucherStatusRepository.findAll();
        assertThat(voucherStatusList).hasSize(databaseSizeBeforeUpdate);
        VoucherStatus testVoucherStatus = voucherStatusList.get(voucherStatusList.size() - 1);
        assertThat(testVoucherStatus.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingVoucherStatus() throws Exception {
        int databaseSizeBeforeUpdate = voucherStatusRepository.findAll().size();
        voucherStatus.setId(count.incrementAndGet());

        // Create the VoucherStatus
        VoucherStatusDTO voucherStatusDTO = voucherStatusMapper.toDto(voucherStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoucherStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, voucherStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voucherStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoucherStatus in the database
        List<VoucherStatus> voucherStatusList = voucherStatusRepository.findAll();
        assertThat(voucherStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVoucherStatus() throws Exception {
        int databaseSizeBeforeUpdate = voucherStatusRepository.findAll().size();
        voucherStatus.setId(count.incrementAndGet());

        // Create the VoucherStatus
        VoucherStatusDTO voucherStatusDTO = voucherStatusMapper.toDto(voucherStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voucherStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoucherStatus in the database
        List<VoucherStatus> voucherStatusList = voucherStatusRepository.findAll();
        assertThat(voucherStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVoucherStatus() throws Exception {
        int databaseSizeBeforeUpdate = voucherStatusRepository.findAll().size();
        voucherStatus.setId(count.incrementAndGet());

        // Create the VoucherStatus
        VoucherStatusDTO voucherStatusDTO = voucherStatusMapper.toDto(voucherStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherStatusMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voucherStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VoucherStatus in the database
        List<VoucherStatus> voucherStatusList = voucherStatusRepository.findAll();
        assertThat(voucherStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVoucherStatusWithPatch() throws Exception {
        // Initialize the database
        voucherStatusRepository.saveAndFlush(voucherStatus);

        int databaseSizeBeforeUpdate = voucherStatusRepository.findAll().size();

        // Update the voucherStatus using partial update
        VoucherStatus partialUpdatedVoucherStatus = new VoucherStatus();
        partialUpdatedVoucherStatus.setId(voucherStatus.getId());

        partialUpdatedVoucherStatus.name(UPDATED_NAME);

        restVoucherStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoucherStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVoucherStatus))
            )
            .andExpect(status().isOk());

        // Validate the VoucherStatus in the database
        List<VoucherStatus> voucherStatusList = voucherStatusRepository.findAll();
        assertThat(voucherStatusList).hasSize(databaseSizeBeforeUpdate);
        VoucherStatus testVoucherStatus = voucherStatusList.get(voucherStatusList.size() - 1);
        assertThat(testVoucherStatus.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateVoucherStatusWithPatch() throws Exception {
        // Initialize the database
        voucherStatusRepository.saveAndFlush(voucherStatus);

        int databaseSizeBeforeUpdate = voucherStatusRepository.findAll().size();

        // Update the voucherStatus using partial update
        VoucherStatus partialUpdatedVoucherStatus = new VoucherStatus();
        partialUpdatedVoucherStatus.setId(voucherStatus.getId());

        partialUpdatedVoucherStatus.name(UPDATED_NAME);

        restVoucherStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoucherStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVoucherStatus))
            )
            .andExpect(status().isOk());

        // Validate the VoucherStatus in the database
        List<VoucherStatus> voucherStatusList = voucherStatusRepository.findAll();
        assertThat(voucherStatusList).hasSize(databaseSizeBeforeUpdate);
        VoucherStatus testVoucherStatus = voucherStatusList.get(voucherStatusList.size() - 1);
        assertThat(testVoucherStatus.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingVoucherStatus() throws Exception {
        int databaseSizeBeforeUpdate = voucherStatusRepository.findAll().size();
        voucherStatus.setId(count.incrementAndGet());

        // Create the VoucherStatus
        VoucherStatusDTO voucherStatusDTO = voucherStatusMapper.toDto(voucherStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoucherStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, voucherStatusDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(voucherStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoucherStatus in the database
        List<VoucherStatus> voucherStatusList = voucherStatusRepository.findAll();
        assertThat(voucherStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVoucherStatus() throws Exception {
        int databaseSizeBeforeUpdate = voucherStatusRepository.findAll().size();
        voucherStatus.setId(count.incrementAndGet());

        // Create the VoucherStatus
        VoucherStatusDTO voucherStatusDTO = voucherStatusMapper.toDto(voucherStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(voucherStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoucherStatus in the database
        List<VoucherStatus> voucherStatusList = voucherStatusRepository.findAll();
        assertThat(voucherStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVoucherStatus() throws Exception {
        int databaseSizeBeforeUpdate = voucherStatusRepository.findAll().size();
        voucherStatus.setId(count.incrementAndGet());

        // Create the VoucherStatus
        VoucherStatusDTO voucherStatusDTO = voucherStatusMapper.toDto(voucherStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherStatusMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(voucherStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VoucherStatus in the database
        List<VoucherStatus> voucherStatusList = voucherStatusRepository.findAll();
        assertThat(voucherStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVoucherStatus() throws Exception {
        // Initialize the database
        voucherStatusRepository.saveAndFlush(voucherStatus);

        int databaseSizeBeforeDelete = voucherStatusRepository.findAll().size();

        // Delete the voucherStatus
        restVoucherStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, voucherStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VoucherStatus> voucherStatusList = voucherStatusRepository.findAll();
        assertThat(voucherStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
