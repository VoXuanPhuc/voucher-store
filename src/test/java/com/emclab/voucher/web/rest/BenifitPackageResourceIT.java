package com.emclab.voucher.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.emclab.voucher.IntegrationTest;
import com.emclab.voucher.domain.BenifitPackage;
import com.emclab.voucher.repository.BenifitPackageRepository;
import com.emclab.voucher.service.dto.BenifitPackageDTO;
import com.emclab.voucher.service.mapper.BenifitPackageMapper;
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
 * Integration tests for the {@link BenifitPackageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BenifitPackageResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_COST = 1L;
    private static final Long UPDATED_COST = 2L;

    private static final String DEFAULT_TIME = "AAAAAAAAAA";
    private static final String UPDATED_TIME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/benifit-packages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BenifitPackageRepository benifitPackageRepository;

    @Autowired
    private BenifitPackageMapper benifitPackageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBenifitPackageMockMvc;

    private BenifitPackage benifitPackage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BenifitPackage createEntity(EntityManager em) {
        BenifitPackage benifitPackage = new BenifitPackage()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .cost(DEFAULT_COST)
            .time(DEFAULT_TIME);
        return benifitPackage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BenifitPackage createUpdatedEntity(EntityManager em) {
        BenifitPackage benifitPackage = new BenifitPackage()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .cost(UPDATED_COST)
            .time(UPDATED_TIME);
        return benifitPackage;
    }

    @BeforeEach
    public void initTest() {
        benifitPackage = createEntity(em);
    }

    @Test
    @Transactional
    void createBenifitPackage() throws Exception {
        int databaseSizeBeforeCreate = benifitPackageRepository.findAll().size();
        // Create the BenifitPackage
        BenifitPackageDTO benifitPackageDTO = benifitPackageMapper.toDto(benifitPackage);
        restBenifitPackageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benifitPackageDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BenifitPackage in the database
        List<BenifitPackage> benifitPackageList = benifitPackageRepository.findAll();
        assertThat(benifitPackageList).hasSize(databaseSizeBeforeCreate + 1);
        BenifitPackage testBenifitPackage = benifitPackageList.get(benifitPackageList.size() - 1);
        assertThat(testBenifitPackage.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBenifitPackage.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBenifitPackage.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testBenifitPackage.getTime()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    void createBenifitPackageWithExistingId() throws Exception {
        // Create the BenifitPackage with an existing ID
        benifitPackage.setId(1L);
        BenifitPackageDTO benifitPackageDTO = benifitPackageMapper.toDto(benifitPackage);

        int databaseSizeBeforeCreate = benifitPackageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBenifitPackageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benifitPackageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BenifitPackage in the database
        List<BenifitPackage> benifitPackageList = benifitPackageRepository.findAll();
        assertThat(benifitPackageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = benifitPackageRepository.findAll().size();
        // set the field null
        benifitPackage.setName(null);

        // Create the BenifitPackage, which fails.
        BenifitPackageDTO benifitPackageDTO = benifitPackageMapper.toDto(benifitPackage);

        restBenifitPackageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benifitPackageDTO))
            )
            .andExpect(status().isBadRequest());

        List<BenifitPackage> benifitPackageList = benifitPackageRepository.findAll();
        assertThat(benifitPackageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = benifitPackageRepository.findAll().size();
        // set the field null
        benifitPackage.setCost(null);

        // Create the BenifitPackage, which fails.
        BenifitPackageDTO benifitPackageDTO = benifitPackageMapper.toDto(benifitPackage);

        restBenifitPackageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benifitPackageDTO))
            )
            .andExpect(status().isBadRequest());

        List<BenifitPackage> benifitPackageList = benifitPackageRepository.findAll();
        assertThat(benifitPackageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = benifitPackageRepository.findAll().size();
        // set the field null
        benifitPackage.setTime(null);

        // Create the BenifitPackage, which fails.
        BenifitPackageDTO benifitPackageDTO = benifitPackageMapper.toDto(benifitPackage);

        restBenifitPackageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benifitPackageDTO))
            )
            .andExpect(status().isBadRequest());

        List<BenifitPackage> benifitPackageList = benifitPackageRepository.findAll();
        assertThat(benifitPackageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBenifitPackages() throws Exception {
        // Initialize the database
        benifitPackageRepository.saveAndFlush(benifitPackage);

        // Get all the benifitPackageList
        restBenifitPackageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(benifitPackage.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.intValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME)));
    }

    @Test
    @Transactional
    void getBenifitPackage() throws Exception {
        // Initialize the database
        benifitPackageRepository.saveAndFlush(benifitPackage);

        // Get the benifitPackage
        restBenifitPackageMockMvc
            .perform(get(ENTITY_API_URL_ID, benifitPackage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(benifitPackage.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.intValue()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME));
    }

    @Test
    @Transactional
    void getNonExistingBenifitPackage() throws Exception {
        // Get the benifitPackage
        restBenifitPackageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBenifitPackage() throws Exception {
        // Initialize the database
        benifitPackageRepository.saveAndFlush(benifitPackage);

        int databaseSizeBeforeUpdate = benifitPackageRepository.findAll().size();

        // Update the benifitPackage
        BenifitPackage updatedBenifitPackage = benifitPackageRepository.findById(benifitPackage.getId()).get();
        // Disconnect from session so that the updates on updatedBenifitPackage are not directly saved in db
        em.detach(updatedBenifitPackage);
        updatedBenifitPackage.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).cost(UPDATED_COST).time(UPDATED_TIME);
        BenifitPackageDTO benifitPackageDTO = benifitPackageMapper.toDto(updatedBenifitPackage);

        restBenifitPackageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, benifitPackageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(benifitPackageDTO))
            )
            .andExpect(status().isOk());

        // Validate the BenifitPackage in the database
        List<BenifitPackage> benifitPackageList = benifitPackageRepository.findAll();
        assertThat(benifitPackageList).hasSize(databaseSizeBeforeUpdate);
        BenifitPackage testBenifitPackage = benifitPackageList.get(benifitPackageList.size() - 1);
        assertThat(testBenifitPackage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBenifitPackage.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBenifitPackage.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testBenifitPackage.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    void putNonExistingBenifitPackage() throws Exception {
        int databaseSizeBeforeUpdate = benifitPackageRepository.findAll().size();
        benifitPackage.setId(count.incrementAndGet());

        // Create the BenifitPackage
        BenifitPackageDTO benifitPackageDTO = benifitPackageMapper.toDto(benifitPackage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBenifitPackageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, benifitPackageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(benifitPackageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BenifitPackage in the database
        List<BenifitPackage> benifitPackageList = benifitPackageRepository.findAll();
        assertThat(benifitPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBenifitPackage() throws Exception {
        int databaseSizeBeforeUpdate = benifitPackageRepository.findAll().size();
        benifitPackage.setId(count.incrementAndGet());

        // Create the BenifitPackage
        BenifitPackageDTO benifitPackageDTO = benifitPackageMapper.toDto(benifitPackage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenifitPackageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(benifitPackageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BenifitPackage in the database
        List<BenifitPackage> benifitPackageList = benifitPackageRepository.findAll();
        assertThat(benifitPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBenifitPackage() throws Exception {
        int databaseSizeBeforeUpdate = benifitPackageRepository.findAll().size();
        benifitPackage.setId(count.incrementAndGet());

        // Create the BenifitPackage
        BenifitPackageDTO benifitPackageDTO = benifitPackageMapper.toDto(benifitPackage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenifitPackageMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benifitPackageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BenifitPackage in the database
        List<BenifitPackage> benifitPackageList = benifitPackageRepository.findAll();
        assertThat(benifitPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBenifitPackageWithPatch() throws Exception {
        // Initialize the database
        benifitPackageRepository.saveAndFlush(benifitPackage);

        int databaseSizeBeforeUpdate = benifitPackageRepository.findAll().size();

        // Update the benifitPackage using partial update
        BenifitPackage partialUpdatedBenifitPackage = new BenifitPackage();
        partialUpdatedBenifitPackage.setId(benifitPackage.getId());

        partialUpdatedBenifitPackage.name(UPDATED_NAME).time(UPDATED_TIME);

        restBenifitPackageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBenifitPackage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBenifitPackage))
            )
            .andExpect(status().isOk());

        // Validate the BenifitPackage in the database
        List<BenifitPackage> benifitPackageList = benifitPackageRepository.findAll();
        assertThat(benifitPackageList).hasSize(databaseSizeBeforeUpdate);
        BenifitPackage testBenifitPackage = benifitPackageList.get(benifitPackageList.size() - 1);
        assertThat(testBenifitPackage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBenifitPackage.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBenifitPackage.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testBenifitPackage.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    void fullUpdateBenifitPackageWithPatch() throws Exception {
        // Initialize the database
        benifitPackageRepository.saveAndFlush(benifitPackage);

        int databaseSizeBeforeUpdate = benifitPackageRepository.findAll().size();

        // Update the benifitPackage using partial update
        BenifitPackage partialUpdatedBenifitPackage = new BenifitPackage();
        partialUpdatedBenifitPackage.setId(benifitPackage.getId());

        partialUpdatedBenifitPackage.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).cost(UPDATED_COST).time(UPDATED_TIME);

        restBenifitPackageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBenifitPackage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBenifitPackage))
            )
            .andExpect(status().isOk());

        // Validate the BenifitPackage in the database
        List<BenifitPackage> benifitPackageList = benifitPackageRepository.findAll();
        assertThat(benifitPackageList).hasSize(databaseSizeBeforeUpdate);
        BenifitPackage testBenifitPackage = benifitPackageList.get(benifitPackageList.size() - 1);
        assertThat(testBenifitPackage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBenifitPackage.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBenifitPackage.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testBenifitPackage.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingBenifitPackage() throws Exception {
        int databaseSizeBeforeUpdate = benifitPackageRepository.findAll().size();
        benifitPackage.setId(count.incrementAndGet());

        // Create the BenifitPackage
        BenifitPackageDTO benifitPackageDTO = benifitPackageMapper.toDto(benifitPackage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBenifitPackageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, benifitPackageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(benifitPackageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BenifitPackage in the database
        List<BenifitPackage> benifitPackageList = benifitPackageRepository.findAll();
        assertThat(benifitPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBenifitPackage() throws Exception {
        int databaseSizeBeforeUpdate = benifitPackageRepository.findAll().size();
        benifitPackage.setId(count.incrementAndGet());

        // Create the BenifitPackage
        BenifitPackageDTO benifitPackageDTO = benifitPackageMapper.toDto(benifitPackage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenifitPackageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(benifitPackageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BenifitPackage in the database
        List<BenifitPackage> benifitPackageList = benifitPackageRepository.findAll();
        assertThat(benifitPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBenifitPackage() throws Exception {
        int databaseSizeBeforeUpdate = benifitPackageRepository.findAll().size();
        benifitPackage.setId(count.incrementAndGet());

        // Create the BenifitPackage
        BenifitPackageDTO benifitPackageDTO = benifitPackageMapper.toDto(benifitPackage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenifitPackageMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(benifitPackageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BenifitPackage in the database
        List<BenifitPackage> benifitPackageList = benifitPackageRepository.findAll();
        assertThat(benifitPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBenifitPackage() throws Exception {
        // Initialize the database
        benifitPackageRepository.saveAndFlush(benifitPackage);

        int databaseSizeBeforeDelete = benifitPackageRepository.findAll().size();

        // Delete the benifitPackage
        restBenifitPackageMockMvc
            .perform(delete(ENTITY_API_URL_ID, benifitPackage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BenifitPackage> benifitPackageList = benifitPackageRepository.findAll();
        assertThat(benifitPackageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
