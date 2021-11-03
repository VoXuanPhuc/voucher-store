package com.emclab.voucher.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.emclab.voucher.IntegrationTest;
import com.emclab.voucher.domain.RelationshipType;
import com.emclab.voucher.repository.RelationshipTypeRepository;
import com.emclab.voucher.service.dto.RelationshipTypeDTO;
import com.emclab.voucher.service.mapper.RelationshipTypeMapper;
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
 * Integration tests for the {@link RelationshipTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RelationshipTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/relationship-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RelationshipTypeRepository relationshipTypeRepository;

    @Autowired
    private RelationshipTypeMapper relationshipTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRelationshipTypeMockMvc;

    private RelationshipType relationshipType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RelationshipType createEntity(EntityManager em) {
        RelationshipType relationshipType = new RelationshipType().name(DEFAULT_NAME);
        return relationshipType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RelationshipType createUpdatedEntity(EntityManager em) {
        RelationshipType relationshipType = new RelationshipType().name(UPDATED_NAME);
        return relationshipType;
    }

    @BeforeEach
    public void initTest() {
        relationshipType = createEntity(em);
    }

    @Test
    @Transactional
    void createRelationshipType() throws Exception {
        int databaseSizeBeforeCreate = relationshipTypeRepository.findAll().size();
        // Create the RelationshipType
        RelationshipTypeDTO relationshipTypeDTO = relationshipTypeMapper.toDto(relationshipType);
        restRelationshipTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(relationshipTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RelationshipType in the database
        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeCreate + 1);
        RelationshipType testRelationshipType = relationshipTypeList.get(relationshipTypeList.size() - 1);
        assertThat(testRelationshipType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createRelationshipTypeWithExistingId() throws Exception {
        // Create the RelationshipType with an existing ID
        relationshipType.setId(1L);
        RelationshipTypeDTO relationshipTypeDTO = relationshipTypeMapper.toDto(relationshipType);

        int databaseSizeBeforeCreate = relationshipTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRelationshipTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(relationshipTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelationshipType in the database
        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = relationshipTypeRepository.findAll().size();
        // set the field null
        relationshipType.setName(null);

        // Create the RelationshipType, which fails.
        RelationshipTypeDTO relationshipTypeDTO = relationshipTypeMapper.toDto(relationshipType);

        restRelationshipTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(relationshipTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRelationshipTypes() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get all the relationshipTypeList
        restRelationshipTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relationshipType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getRelationshipType() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get the relationshipType
        restRelationshipTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, relationshipType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(relationshipType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingRelationshipType() throws Exception {
        // Get the relationshipType
        restRelationshipTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRelationshipType() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        int databaseSizeBeforeUpdate = relationshipTypeRepository.findAll().size();

        // Update the relationshipType
        RelationshipType updatedRelationshipType = relationshipTypeRepository.findById(relationshipType.getId()).get();
        // Disconnect from session so that the updates on updatedRelationshipType are not directly saved in db
        em.detach(updatedRelationshipType);
        updatedRelationshipType.name(UPDATED_NAME);
        RelationshipTypeDTO relationshipTypeDTO = relationshipTypeMapper.toDto(updatedRelationshipType);

        restRelationshipTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, relationshipTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relationshipTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the RelationshipType in the database
        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeUpdate);
        RelationshipType testRelationshipType = relationshipTypeList.get(relationshipTypeList.size() - 1);
        assertThat(testRelationshipType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingRelationshipType() throws Exception {
        int databaseSizeBeforeUpdate = relationshipTypeRepository.findAll().size();
        relationshipType.setId(count.incrementAndGet());

        // Create the RelationshipType
        RelationshipTypeDTO relationshipTypeDTO = relationshipTypeMapper.toDto(relationshipType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelationshipTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, relationshipTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relationshipTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelationshipType in the database
        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRelationshipType() throws Exception {
        int databaseSizeBeforeUpdate = relationshipTypeRepository.findAll().size();
        relationshipType.setId(count.incrementAndGet());

        // Create the RelationshipType
        RelationshipTypeDTO relationshipTypeDTO = relationshipTypeMapper.toDto(relationshipType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelationshipTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relationshipTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelationshipType in the database
        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRelationshipType() throws Exception {
        int databaseSizeBeforeUpdate = relationshipTypeRepository.findAll().size();
        relationshipType.setId(count.incrementAndGet());

        // Create the RelationshipType
        RelationshipTypeDTO relationshipTypeDTO = relationshipTypeMapper.toDto(relationshipType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelationshipTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(relationshipTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RelationshipType in the database
        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRelationshipTypeWithPatch() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        int databaseSizeBeforeUpdate = relationshipTypeRepository.findAll().size();

        // Update the relationshipType using partial update
        RelationshipType partialUpdatedRelationshipType = new RelationshipType();
        partialUpdatedRelationshipType.setId(relationshipType.getId());

        restRelationshipTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRelationshipType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRelationshipType))
            )
            .andExpect(status().isOk());

        // Validate the RelationshipType in the database
        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeUpdate);
        RelationshipType testRelationshipType = relationshipTypeList.get(relationshipTypeList.size() - 1);
        assertThat(testRelationshipType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateRelationshipTypeWithPatch() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        int databaseSizeBeforeUpdate = relationshipTypeRepository.findAll().size();

        // Update the relationshipType using partial update
        RelationshipType partialUpdatedRelationshipType = new RelationshipType();
        partialUpdatedRelationshipType.setId(relationshipType.getId());

        partialUpdatedRelationshipType.name(UPDATED_NAME);

        restRelationshipTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRelationshipType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRelationshipType))
            )
            .andExpect(status().isOk());

        // Validate the RelationshipType in the database
        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeUpdate);
        RelationshipType testRelationshipType = relationshipTypeList.get(relationshipTypeList.size() - 1);
        assertThat(testRelationshipType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingRelationshipType() throws Exception {
        int databaseSizeBeforeUpdate = relationshipTypeRepository.findAll().size();
        relationshipType.setId(count.incrementAndGet());

        // Create the RelationshipType
        RelationshipTypeDTO relationshipTypeDTO = relationshipTypeMapper.toDto(relationshipType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelationshipTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, relationshipTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(relationshipTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelationshipType in the database
        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRelationshipType() throws Exception {
        int databaseSizeBeforeUpdate = relationshipTypeRepository.findAll().size();
        relationshipType.setId(count.incrementAndGet());

        // Create the RelationshipType
        RelationshipTypeDTO relationshipTypeDTO = relationshipTypeMapper.toDto(relationshipType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelationshipTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(relationshipTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelationshipType in the database
        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRelationshipType() throws Exception {
        int databaseSizeBeforeUpdate = relationshipTypeRepository.findAll().size();
        relationshipType.setId(count.incrementAndGet());

        // Create the RelationshipType
        RelationshipTypeDTO relationshipTypeDTO = relationshipTypeMapper.toDto(relationshipType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelationshipTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(relationshipTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RelationshipType in the database
        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRelationshipType() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        int databaseSizeBeforeDelete = relationshipTypeRepository.findAll().size();

        // Delete the relationshipType
        restRelationshipTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, relationshipType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
