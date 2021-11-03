package com.emclab.voucher.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.emclab.voucher.IntegrationTest;
import com.emclab.voucher.domain.ServiceType;
import com.emclab.voucher.repository.ServiceTypeRepository;
import com.emclab.voucher.service.dto.ServiceTypeDTO;
import com.emclab.voucher.service.mapper.ServiceTypeMapper;
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
 * Integration tests for the {@link ServiceTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServiceTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/service-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    @Autowired
    private ServiceTypeMapper serviceTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceTypeMockMvc;

    private ServiceType serviceType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceType createEntity(EntityManager em) {
        ServiceType serviceType = new ServiceType().name(DEFAULT_NAME);
        return serviceType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceType createUpdatedEntity(EntityManager em) {
        ServiceType serviceType = new ServiceType().name(UPDATED_NAME);
        return serviceType;
    }

    @BeforeEach
    public void initTest() {
        serviceType = createEntity(em);
    }

    @Test
    @Transactional
    void createServiceType() throws Exception {
        int databaseSizeBeforeCreate = serviceTypeRepository.findAll().size();
        // Create the ServiceType
        ServiceTypeDTO serviceTypeDTO = serviceTypeMapper.toDto(serviceType);
        restServiceTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(serviceTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ServiceType in the database
        List<ServiceType> serviceTypeList = serviceTypeRepository.findAll();
        assertThat(serviceTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceType testServiceType = serviceTypeList.get(serviceTypeList.size() - 1);
        assertThat(testServiceType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createServiceTypeWithExistingId() throws Exception {
        // Create the ServiceType with an existing ID
        serviceType.setId(1L);
        ServiceTypeDTO serviceTypeDTO = serviceTypeMapper.toDto(serviceType);

        int databaseSizeBeforeCreate = serviceTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(serviceTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceType in the database
        List<ServiceType> serviceTypeList = serviceTypeRepository.findAll();
        assertThat(serviceTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceTypeRepository.findAll().size();
        // set the field null
        serviceType.setName(null);

        // Create the ServiceType, which fails.
        ServiceTypeDTO serviceTypeDTO = serviceTypeMapper.toDto(serviceType);

        restServiceTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(serviceTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<ServiceType> serviceTypeList = serviceTypeRepository.findAll();
        assertThat(serviceTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllServiceTypes() throws Exception {
        // Initialize the database
        serviceTypeRepository.saveAndFlush(serviceType);

        // Get all the serviceTypeList
        restServiceTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getServiceType() throws Exception {
        // Initialize the database
        serviceTypeRepository.saveAndFlush(serviceType);

        // Get the serviceType
        restServiceTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, serviceType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingServiceType() throws Exception {
        // Get the serviceType
        restServiceTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewServiceType() throws Exception {
        // Initialize the database
        serviceTypeRepository.saveAndFlush(serviceType);

        int databaseSizeBeforeUpdate = serviceTypeRepository.findAll().size();

        // Update the serviceType
        ServiceType updatedServiceType = serviceTypeRepository.findById(serviceType.getId()).get();
        // Disconnect from session so that the updates on updatedServiceType are not directly saved in db
        em.detach(updatedServiceType);
        updatedServiceType.name(UPDATED_NAME);
        ServiceTypeDTO serviceTypeDTO = serviceTypeMapper.toDto(updatedServiceType);

        restServiceTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(serviceTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the ServiceType in the database
        List<ServiceType> serviceTypeList = serviceTypeRepository.findAll();
        assertThat(serviceTypeList).hasSize(databaseSizeBeforeUpdate);
        ServiceType testServiceType = serviceTypeList.get(serviceTypeList.size() - 1);
        assertThat(testServiceType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingServiceType() throws Exception {
        int databaseSizeBeforeUpdate = serviceTypeRepository.findAll().size();
        serviceType.setId(count.incrementAndGet());

        // Create the ServiceType
        ServiceTypeDTO serviceTypeDTO = serviceTypeMapper.toDto(serviceType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(serviceTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceType in the database
        List<ServiceType> serviceTypeList = serviceTypeRepository.findAll();
        assertThat(serviceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServiceType() throws Exception {
        int databaseSizeBeforeUpdate = serviceTypeRepository.findAll().size();
        serviceType.setId(count.incrementAndGet());

        // Create the ServiceType
        ServiceTypeDTO serviceTypeDTO = serviceTypeMapper.toDto(serviceType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(serviceTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceType in the database
        List<ServiceType> serviceTypeList = serviceTypeRepository.findAll();
        assertThat(serviceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServiceType() throws Exception {
        int databaseSizeBeforeUpdate = serviceTypeRepository.findAll().size();
        serviceType.setId(count.incrementAndGet());

        // Create the ServiceType
        ServiceTypeDTO serviceTypeDTO = serviceTypeMapper.toDto(serviceType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(serviceTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceType in the database
        List<ServiceType> serviceTypeList = serviceTypeRepository.findAll();
        assertThat(serviceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServiceTypeWithPatch() throws Exception {
        // Initialize the database
        serviceTypeRepository.saveAndFlush(serviceType);

        int databaseSizeBeforeUpdate = serviceTypeRepository.findAll().size();

        // Update the serviceType using partial update
        ServiceType partialUpdatedServiceType = new ServiceType();
        partialUpdatedServiceType.setId(serviceType.getId());

        restServiceTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServiceType))
            )
            .andExpect(status().isOk());

        // Validate the ServiceType in the database
        List<ServiceType> serviceTypeList = serviceTypeRepository.findAll();
        assertThat(serviceTypeList).hasSize(databaseSizeBeforeUpdate);
        ServiceType testServiceType = serviceTypeList.get(serviceTypeList.size() - 1);
        assertThat(testServiceType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateServiceTypeWithPatch() throws Exception {
        // Initialize the database
        serviceTypeRepository.saveAndFlush(serviceType);

        int databaseSizeBeforeUpdate = serviceTypeRepository.findAll().size();

        // Update the serviceType using partial update
        ServiceType partialUpdatedServiceType = new ServiceType();
        partialUpdatedServiceType.setId(serviceType.getId());

        partialUpdatedServiceType.name(UPDATED_NAME);

        restServiceTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServiceType))
            )
            .andExpect(status().isOk());

        // Validate the ServiceType in the database
        List<ServiceType> serviceTypeList = serviceTypeRepository.findAll();
        assertThat(serviceTypeList).hasSize(databaseSizeBeforeUpdate);
        ServiceType testServiceType = serviceTypeList.get(serviceTypeList.size() - 1);
        assertThat(testServiceType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingServiceType() throws Exception {
        int databaseSizeBeforeUpdate = serviceTypeRepository.findAll().size();
        serviceType.setId(count.incrementAndGet());

        // Create the ServiceType
        ServiceTypeDTO serviceTypeDTO = serviceTypeMapper.toDto(serviceType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, serviceTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(serviceTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceType in the database
        List<ServiceType> serviceTypeList = serviceTypeRepository.findAll();
        assertThat(serviceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServiceType() throws Exception {
        int databaseSizeBeforeUpdate = serviceTypeRepository.findAll().size();
        serviceType.setId(count.incrementAndGet());

        // Create the ServiceType
        ServiceTypeDTO serviceTypeDTO = serviceTypeMapper.toDto(serviceType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(serviceTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceType in the database
        List<ServiceType> serviceTypeList = serviceTypeRepository.findAll();
        assertThat(serviceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServiceType() throws Exception {
        int databaseSizeBeforeUpdate = serviceTypeRepository.findAll().size();
        serviceType.setId(count.incrementAndGet());

        // Create the ServiceType
        ServiceTypeDTO serviceTypeDTO = serviceTypeMapper.toDto(serviceType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(serviceTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceType in the database
        List<ServiceType> serviceTypeList = serviceTypeRepository.findAll();
        assertThat(serviceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServiceType() throws Exception {
        // Initialize the database
        serviceTypeRepository.saveAndFlush(serviceType);

        int databaseSizeBeforeDelete = serviceTypeRepository.findAll().size();

        // Delete the serviceType
        restServiceTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, serviceType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceType> serviceTypeList = serviceTypeRepository.findAll();
        assertThat(serviceTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
