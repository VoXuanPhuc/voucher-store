package com.emclab.voucher.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.emclab.voucher.IntegrationTest;
import com.emclab.voucher.domain.StoreUser;
import com.emclab.voucher.repository.StoreUserRepository;
import com.emclab.voucher.service.dto.StoreUserDTO;
import com.emclab.voucher.service.mapper.StoreUserMapper;
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
 * Integration tests for the {@link StoreUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StoreUserResourceIT {

    private static final String ENTITY_API_URL = "/api/store-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StoreUserRepository storeUserRepository;

    @Autowired
    private StoreUserMapper storeUserMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStoreUserMockMvc;

    private StoreUser storeUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StoreUser createEntity(EntityManager em) {
        StoreUser storeUser = new StoreUser();
        return storeUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StoreUser createUpdatedEntity(EntityManager em) {
        StoreUser storeUser = new StoreUser();
        return storeUser;
    }

    @BeforeEach
    public void initTest() {
        storeUser = createEntity(em);
    }

    @Test
    @Transactional
    void createStoreUser() throws Exception {
        int databaseSizeBeforeCreate = storeUserRepository.findAll().size();
        // Create the StoreUser
        StoreUserDTO storeUserDTO = storeUserMapper.toDto(storeUser);
        restStoreUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storeUserDTO)))
            .andExpect(status().isCreated());

        // Validate the StoreUser in the database
        List<StoreUser> storeUserList = storeUserRepository.findAll();
        assertThat(storeUserList).hasSize(databaseSizeBeforeCreate + 1);
        StoreUser testStoreUser = storeUserList.get(storeUserList.size() - 1);
    }

    @Test
    @Transactional
    void createStoreUserWithExistingId() throws Exception {
        // Create the StoreUser with an existing ID
        storeUser.setId(1L);
        StoreUserDTO storeUserDTO = storeUserMapper.toDto(storeUser);

        int databaseSizeBeforeCreate = storeUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStoreUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storeUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StoreUser in the database
        List<StoreUser> storeUserList = storeUserRepository.findAll();
        assertThat(storeUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStoreUsers() throws Exception {
        // Initialize the database
        storeUserRepository.saveAndFlush(storeUser);

        // Get all the storeUserList
        restStoreUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(storeUser.getId().intValue())));
    }

    @Test
    @Transactional
    void getStoreUser() throws Exception {
        // Initialize the database
        storeUserRepository.saveAndFlush(storeUser);

        // Get the storeUser
        restStoreUserMockMvc
            .perform(get(ENTITY_API_URL_ID, storeUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(storeUser.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingStoreUser() throws Exception {
        // Get the storeUser
        restStoreUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStoreUser() throws Exception {
        // Initialize the database
        storeUserRepository.saveAndFlush(storeUser);

        int databaseSizeBeforeUpdate = storeUserRepository.findAll().size();

        // Update the storeUser
        StoreUser updatedStoreUser = storeUserRepository.findById(storeUser.getId()).get();
        // Disconnect from session so that the updates on updatedStoreUser are not directly saved in db
        em.detach(updatedStoreUser);
        StoreUserDTO storeUserDTO = storeUserMapper.toDto(updatedStoreUser);

        restStoreUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, storeUserDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(storeUserDTO))
            )
            .andExpect(status().isOk());

        // Validate the StoreUser in the database
        List<StoreUser> storeUserList = storeUserRepository.findAll();
        assertThat(storeUserList).hasSize(databaseSizeBeforeUpdate);
        StoreUser testStoreUser = storeUserList.get(storeUserList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingStoreUser() throws Exception {
        int databaseSizeBeforeUpdate = storeUserRepository.findAll().size();
        storeUser.setId(count.incrementAndGet());

        // Create the StoreUser
        StoreUserDTO storeUserDTO = storeUserMapper.toDto(storeUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStoreUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, storeUserDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(storeUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StoreUser in the database
        List<StoreUser> storeUserList = storeUserRepository.findAll();
        assertThat(storeUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStoreUser() throws Exception {
        int databaseSizeBeforeUpdate = storeUserRepository.findAll().size();
        storeUser.setId(count.incrementAndGet());

        // Create the StoreUser
        StoreUserDTO storeUserDTO = storeUserMapper.toDto(storeUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStoreUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(storeUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StoreUser in the database
        List<StoreUser> storeUserList = storeUserRepository.findAll();
        assertThat(storeUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStoreUser() throws Exception {
        int databaseSizeBeforeUpdate = storeUserRepository.findAll().size();
        storeUser.setId(count.incrementAndGet());

        // Create the StoreUser
        StoreUserDTO storeUserDTO = storeUserMapper.toDto(storeUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStoreUserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storeUserDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StoreUser in the database
        List<StoreUser> storeUserList = storeUserRepository.findAll();
        assertThat(storeUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStoreUserWithPatch() throws Exception {
        // Initialize the database
        storeUserRepository.saveAndFlush(storeUser);

        int databaseSizeBeforeUpdate = storeUserRepository.findAll().size();

        // Update the storeUser using partial update
        StoreUser partialUpdatedStoreUser = new StoreUser();
        partialUpdatedStoreUser.setId(storeUser.getId());

        restStoreUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStoreUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStoreUser))
            )
            .andExpect(status().isOk());

        // Validate the StoreUser in the database
        List<StoreUser> storeUserList = storeUserRepository.findAll();
        assertThat(storeUserList).hasSize(databaseSizeBeforeUpdate);
        StoreUser testStoreUser = storeUserList.get(storeUserList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateStoreUserWithPatch() throws Exception {
        // Initialize the database
        storeUserRepository.saveAndFlush(storeUser);

        int databaseSizeBeforeUpdate = storeUserRepository.findAll().size();

        // Update the storeUser using partial update
        StoreUser partialUpdatedStoreUser = new StoreUser();
        partialUpdatedStoreUser.setId(storeUser.getId());

        restStoreUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStoreUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStoreUser))
            )
            .andExpect(status().isOk());

        // Validate the StoreUser in the database
        List<StoreUser> storeUserList = storeUserRepository.findAll();
        assertThat(storeUserList).hasSize(databaseSizeBeforeUpdate);
        StoreUser testStoreUser = storeUserList.get(storeUserList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingStoreUser() throws Exception {
        int databaseSizeBeforeUpdate = storeUserRepository.findAll().size();
        storeUser.setId(count.incrementAndGet());

        // Create the StoreUser
        StoreUserDTO storeUserDTO = storeUserMapper.toDto(storeUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStoreUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, storeUserDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(storeUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StoreUser in the database
        List<StoreUser> storeUserList = storeUserRepository.findAll();
        assertThat(storeUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStoreUser() throws Exception {
        int databaseSizeBeforeUpdate = storeUserRepository.findAll().size();
        storeUser.setId(count.incrementAndGet());

        // Create the StoreUser
        StoreUserDTO storeUserDTO = storeUserMapper.toDto(storeUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStoreUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(storeUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StoreUser in the database
        List<StoreUser> storeUserList = storeUserRepository.findAll();
        assertThat(storeUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStoreUser() throws Exception {
        int databaseSizeBeforeUpdate = storeUserRepository.findAll().size();
        storeUser.setId(count.incrementAndGet());

        // Create the StoreUser
        StoreUserDTO storeUserDTO = storeUserMapper.toDto(storeUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStoreUserMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(storeUserDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StoreUser in the database
        List<StoreUser> storeUserList = storeUserRepository.findAll();
        assertThat(storeUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStoreUser() throws Exception {
        // Initialize the database
        storeUserRepository.saveAndFlush(storeUser);

        int databaseSizeBeforeDelete = storeUserRepository.findAll().size();

        // Delete the storeUser
        restStoreUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, storeUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StoreUser> storeUserList = storeUserRepository.findAll();
        assertThat(storeUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
