package com.emclab.voucher.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.emclab.voucher.IntegrationTest;
import com.emclab.voucher.domain.VoucherCode;
import com.emclab.voucher.repository.VoucherCodeRepository;
import com.emclab.voucher.service.dto.VoucherCodeDTO;
import com.emclab.voucher.service.mapper.VoucherCodeMapper;
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
 * Integration tests for the {@link VoucherCodeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VoucherCodeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/voucher-codes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VoucherCodeRepository voucherCodeRepository;

    @Autowired
    private VoucherCodeMapper voucherCodeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVoucherCodeMockMvc;

    private VoucherCode voucherCode;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VoucherCode createEntity(EntityManager em) {
        VoucherCode voucherCode = new VoucherCode().code(DEFAULT_CODE);
        return voucherCode;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VoucherCode createUpdatedEntity(EntityManager em) {
        VoucherCode voucherCode = new VoucherCode().code(UPDATED_CODE);
        return voucherCode;
    }

    @BeforeEach
    public void initTest() {
        voucherCode = createEntity(em);
    }

    @Test
    @Transactional
    void createVoucherCode() throws Exception {
        int databaseSizeBeforeCreate = voucherCodeRepository.findAll().size();
        // Create the VoucherCode
        VoucherCodeDTO voucherCodeDTO = voucherCodeMapper.toDto(voucherCode);
        restVoucherCodeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voucherCodeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the VoucherCode in the database
        List<VoucherCode> voucherCodeList = voucherCodeRepository.findAll();
        assertThat(voucherCodeList).hasSize(databaseSizeBeforeCreate + 1);
        VoucherCode testVoucherCode = voucherCodeList.get(voucherCodeList.size() - 1);
        assertThat(testVoucherCode.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void createVoucherCodeWithExistingId() throws Exception {
        // Create the VoucherCode with an existing ID
        voucherCode.setId(1L);
        VoucherCodeDTO voucherCodeDTO = voucherCodeMapper.toDto(voucherCode);

        int databaseSizeBeforeCreate = voucherCodeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoucherCodeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voucherCodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoucherCode in the database
        List<VoucherCode> voucherCodeList = voucherCodeRepository.findAll();
        assertThat(voucherCodeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = voucherCodeRepository.findAll().size();
        // set the field null
        voucherCode.setCode(null);

        // Create the VoucherCode, which fails.
        VoucherCodeDTO voucherCodeDTO = voucherCodeMapper.toDto(voucherCode);

        restVoucherCodeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voucherCodeDTO))
            )
            .andExpect(status().isBadRequest());

        List<VoucherCode> voucherCodeList = voucherCodeRepository.findAll();
        assertThat(voucherCodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVoucherCodes() throws Exception {
        // Initialize the database
        voucherCodeRepository.saveAndFlush(voucherCode);

        // Get all the voucherCodeList
        restVoucherCodeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucherCode.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    void getVoucherCode() throws Exception {
        // Initialize the database
        voucherCodeRepository.saveAndFlush(voucherCode);

        // Get the voucherCode
        restVoucherCodeMockMvc
            .perform(get(ENTITY_API_URL_ID, voucherCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(voucherCode.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    void getNonExistingVoucherCode() throws Exception {
        // Get the voucherCode
        restVoucherCodeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVoucherCode() throws Exception {
        // Initialize the database
        voucherCodeRepository.saveAndFlush(voucherCode);

        int databaseSizeBeforeUpdate = voucherCodeRepository.findAll().size();

        // Update the voucherCode
        VoucherCode updatedVoucherCode = voucherCodeRepository.findById(voucherCode.getId()).get();
        // Disconnect from session so that the updates on updatedVoucherCode are not directly saved in db
        em.detach(updatedVoucherCode);
        updatedVoucherCode.code(UPDATED_CODE);
        VoucherCodeDTO voucherCodeDTO = voucherCodeMapper.toDto(updatedVoucherCode);

        restVoucherCodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, voucherCodeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voucherCodeDTO))
            )
            .andExpect(status().isOk());

        // Validate the VoucherCode in the database
        List<VoucherCode> voucherCodeList = voucherCodeRepository.findAll();
        assertThat(voucherCodeList).hasSize(databaseSizeBeforeUpdate);
        VoucherCode testVoucherCode = voucherCodeList.get(voucherCodeList.size() - 1);
        assertThat(testVoucherCode.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void putNonExistingVoucherCode() throws Exception {
        int databaseSizeBeforeUpdate = voucherCodeRepository.findAll().size();
        voucherCode.setId(count.incrementAndGet());

        // Create the VoucherCode
        VoucherCodeDTO voucherCodeDTO = voucherCodeMapper.toDto(voucherCode);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoucherCodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, voucherCodeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voucherCodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoucherCode in the database
        List<VoucherCode> voucherCodeList = voucherCodeRepository.findAll();
        assertThat(voucherCodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVoucherCode() throws Exception {
        int databaseSizeBeforeUpdate = voucherCodeRepository.findAll().size();
        voucherCode.setId(count.incrementAndGet());

        // Create the VoucherCode
        VoucherCodeDTO voucherCodeDTO = voucherCodeMapper.toDto(voucherCode);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherCodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voucherCodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoucherCode in the database
        List<VoucherCode> voucherCodeList = voucherCodeRepository.findAll();
        assertThat(voucherCodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVoucherCode() throws Exception {
        int databaseSizeBeforeUpdate = voucherCodeRepository.findAll().size();
        voucherCode.setId(count.incrementAndGet());

        // Create the VoucherCode
        VoucherCodeDTO voucherCodeDTO = voucherCodeMapper.toDto(voucherCode);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherCodeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voucherCodeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VoucherCode in the database
        List<VoucherCode> voucherCodeList = voucherCodeRepository.findAll();
        assertThat(voucherCodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVoucherCodeWithPatch() throws Exception {
        // Initialize the database
        voucherCodeRepository.saveAndFlush(voucherCode);

        int databaseSizeBeforeUpdate = voucherCodeRepository.findAll().size();

        // Update the voucherCode using partial update
        VoucherCode partialUpdatedVoucherCode = new VoucherCode();
        partialUpdatedVoucherCode.setId(voucherCode.getId());

        restVoucherCodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoucherCode.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVoucherCode))
            )
            .andExpect(status().isOk());

        // Validate the VoucherCode in the database
        List<VoucherCode> voucherCodeList = voucherCodeRepository.findAll();
        assertThat(voucherCodeList).hasSize(databaseSizeBeforeUpdate);
        VoucherCode testVoucherCode = voucherCodeList.get(voucherCodeList.size() - 1);
        assertThat(testVoucherCode.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void fullUpdateVoucherCodeWithPatch() throws Exception {
        // Initialize the database
        voucherCodeRepository.saveAndFlush(voucherCode);

        int databaseSizeBeforeUpdate = voucherCodeRepository.findAll().size();

        // Update the voucherCode using partial update
        VoucherCode partialUpdatedVoucherCode = new VoucherCode();
        partialUpdatedVoucherCode.setId(voucherCode.getId());

        partialUpdatedVoucherCode.code(UPDATED_CODE);

        restVoucherCodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoucherCode.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVoucherCode))
            )
            .andExpect(status().isOk());

        // Validate the VoucherCode in the database
        List<VoucherCode> voucherCodeList = voucherCodeRepository.findAll();
        assertThat(voucherCodeList).hasSize(databaseSizeBeforeUpdate);
        VoucherCode testVoucherCode = voucherCodeList.get(voucherCodeList.size() - 1);
        assertThat(testVoucherCode.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingVoucherCode() throws Exception {
        int databaseSizeBeforeUpdate = voucherCodeRepository.findAll().size();
        voucherCode.setId(count.incrementAndGet());

        // Create the VoucherCode
        VoucherCodeDTO voucherCodeDTO = voucherCodeMapper.toDto(voucherCode);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoucherCodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, voucherCodeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(voucherCodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoucherCode in the database
        List<VoucherCode> voucherCodeList = voucherCodeRepository.findAll();
        assertThat(voucherCodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVoucherCode() throws Exception {
        int databaseSizeBeforeUpdate = voucherCodeRepository.findAll().size();
        voucherCode.setId(count.incrementAndGet());

        // Create the VoucherCode
        VoucherCodeDTO voucherCodeDTO = voucherCodeMapper.toDto(voucherCode);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherCodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(voucherCodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoucherCode in the database
        List<VoucherCode> voucherCodeList = voucherCodeRepository.findAll();
        assertThat(voucherCodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVoucherCode() throws Exception {
        int databaseSizeBeforeUpdate = voucherCodeRepository.findAll().size();
        voucherCode.setId(count.incrementAndGet());

        // Create the VoucherCode
        VoucherCodeDTO voucherCodeDTO = voucherCodeMapper.toDto(voucherCode);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherCodeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(voucherCodeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VoucherCode in the database
        List<VoucherCode> voucherCodeList = voucherCodeRepository.findAll();
        assertThat(voucherCodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVoucherCode() throws Exception {
        // Initialize the database
        voucherCodeRepository.saveAndFlush(voucherCode);

        int databaseSizeBeforeDelete = voucherCodeRepository.findAll().size();

        // Delete the voucherCode
        restVoucherCodeMockMvc
            .perform(delete(ENTITY_API_URL_ID, voucherCode.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VoucherCode> voucherCodeList = voucherCodeRepository.findAll();
        assertThat(voucherCodeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
