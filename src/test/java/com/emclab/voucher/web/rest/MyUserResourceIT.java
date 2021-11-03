package com.emclab.voucher.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.emclab.voucher.IntegrationTest;
import com.emclab.voucher.domain.MyUser;
import com.emclab.voucher.repository.MyUserRepository;
import com.emclab.voucher.service.dto.MyUserDTO;
import com.emclab.voucher.service.mapper.MyUserMapper;
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
 * Integration tests for the {@link MyUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MyUserResourceIT {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/my-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MyUserRepository myUserRepository;

    @Autowired
    private MyUserMapper myUserMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMyUserMockMvc;

    private MyUser myUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyUser createEntity(EntityManager em) {
        MyUser myUser = new MyUser()
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .gender(DEFAULT_GENDER)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL);
        return myUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyUser createUpdatedEntity(EntityManager em) {
        MyUser myUser = new MyUser()
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .gender(UPDATED_GENDER)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL);
        return myUser;
    }

    @BeforeEach
    public void initTest() {
        myUser = createEntity(em);
    }

    @Test
    @Transactional
    void createMyUser() throws Exception {
        int databaseSizeBeforeCreate = myUserRepository.findAll().size();
        // Create the MyUser
        MyUserDTO myUserDTO = myUserMapper.toDto(myUser);
        restMyUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myUserDTO)))
            .andExpect(status().isCreated());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeCreate + 1);
        MyUser testMyUser = myUserList.get(myUserList.size() - 1);
        assertThat(testMyUser.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testMyUser.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testMyUser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testMyUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testMyUser.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testMyUser.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testMyUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void createMyUserWithExistingId() throws Exception {
        // Create the MyUser with an existing ID
        myUser.setId(1L);
        MyUserDTO myUserDTO = myUserMapper.toDto(myUser);

        int databaseSizeBeforeCreate = myUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMyUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = myUserRepository.findAll().size();
        // set the field null
        myUser.setUsername(null);

        // Create the MyUser, which fails.
        MyUserDTO myUserDTO = myUserMapper.toDto(myUser);

        restMyUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myUserDTO)))
            .andExpect(status().isBadRequest());

        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = myUserRepository.findAll().size();
        // set the field null
        myUser.setPassword(null);

        // Create the MyUser, which fails.
        MyUserDTO myUserDTO = myUserMapper.toDto(myUser);

        restMyUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myUserDTO)))
            .andExpect(status().isBadRequest());

        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = myUserRepository.findAll().size();
        // set the field null
        myUser.setFirstName(null);

        // Create the MyUser, which fails.
        MyUserDTO myUserDTO = myUserMapper.toDto(myUser);

        restMyUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myUserDTO)))
            .andExpect(status().isBadRequest());

        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = myUserRepository.findAll().size();
        // set the field null
        myUser.setLastName(null);

        // Create the MyUser, which fails.
        MyUserDTO myUserDTO = myUserMapper.toDto(myUser);

        restMyUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myUserDTO)))
            .andExpect(status().isBadRequest());

        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = myUserRepository.findAll().size();
        // set the field null
        myUser.setGender(null);

        // Create the MyUser, which fails.
        MyUserDTO myUserDTO = myUserMapper.toDto(myUser);

        restMyUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myUserDTO)))
            .andExpect(status().isBadRequest());

        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = myUserRepository.findAll().size();
        // set the field null
        myUser.setPhone(null);

        // Create the MyUser, which fails.
        MyUserDTO myUserDTO = myUserMapper.toDto(myUser);

        restMyUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myUserDTO)))
            .andExpect(status().isBadRequest());

        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = myUserRepository.findAll().size();
        // set the field null
        myUser.setEmail(null);

        // Create the MyUser, which fails.
        MyUserDTO myUserDTO = myUserMapper.toDto(myUser);

        restMyUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myUserDTO)))
            .andExpect(status().isBadRequest());

        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMyUsers() throws Exception {
        // Initialize the database
        myUserRepository.saveAndFlush(myUser);

        // Get all the myUserList
        restMyUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(myUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    void getMyUser() throws Exception {
        // Initialize the database
        myUserRepository.saveAndFlush(myUser);

        // Get the myUser
        restMyUserMockMvc
            .perform(get(ENTITY_API_URL_ID, myUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(myUser.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    void getNonExistingMyUser() throws Exception {
        // Get the myUser
        restMyUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMyUser() throws Exception {
        // Initialize the database
        myUserRepository.saveAndFlush(myUser);

        int databaseSizeBeforeUpdate = myUserRepository.findAll().size();

        // Update the myUser
        MyUser updatedMyUser = myUserRepository.findById(myUser.getId()).get();
        // Disconnect from session so that the updates on updatedMyUser are not directly saved in db
        em.detach(updatedMyUser);
        updatedMyUser
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .gender(UPDATED_GENDER)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL);
        MyUserDTO myUserDTO = myUserMapper.toDto(updatedMyUser);

        restMyUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, myUserDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(myUserDTO))
            )
            .andExpect(status().isOk());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeUpdate);
        MyUser testMyUser = myUserList.get(myUserList.size() - 1);
        assertThat(testMyUser.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testMyUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testMyUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testMyUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testMyUser.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testMyUser.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testMyUser.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void putNonExistingMyUser() throws Exception {
        int databaseSizeBeforeUpdate = myUserRepository.findAll().size();
        myUser.setId(count.incrementAndGet());

        // Create the MyUser
        MyUserDTO myUserDTO = myUserMapper.toDto(myUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMyUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, myUserDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(myUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMyUser() throws Exception {
        int databaseSizeBeforeUpdate = myUserRepository.findAll().size();
        myUser.setId(count.incrementAndGet());

        // Create the MyUser
        MyUserDTO myUserDTO = myUserMapper.toDto(myUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMyUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(myUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMyUser() throws Exception {
        int databaseSizeBeforeUpdate = myUserRepository.findAll().size();
        myUser.setId(count.incrementAndGet());

        // Create the MyUser
        MyUserDTO myUserDTO = myUserMapper.toDto(myUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMyUserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myUserDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMyUserWithPatch() throws Exception {
        // Initialize the database
        myUserRepository.saveAndFlush(myUser);

        int databaseSizeBeforeUpdate = myUserRepository.findAll().size();

        // Update the myUser using partial update
        MyUser partialUpdatedMyUser = new MyUser();
        partialUpdatedMyUser.setId(myUser.getId());

        partialUpdatedMyUser
            .password(UPDATED_PASSWORD)
            .firstName(UPDATED_FIRST_NAME)
            .gender(UPDATED_GENDER)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL);

        restMyUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMyUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMyUser))
            )
            .andExpect(status().isOk());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeUpdate);
        MyUser testMyUser = myUserList.get(myUserList.size() - 1);
        assertThat(testMyUser.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testMyUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testMyUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testMyUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testMyUser.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testMyUser.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testMyUser.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void fullUpdateMyUserWithPatch() throws Exception {
        // Initialize the database
        myUserRepository.saveAndFlush(myUser);

        int databaseSizeBeforeUpdate = myUserRepository.findAll().size();

        // Update the myUser using partial update
        MyUser partialUpdatedMyUser = new MyUser();
        partialUpdatedMyUser.setId(myUser.getId());

        partialUpdatedMyUser
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .gender(UPDATED_GENDER)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL);

        restMyUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMyUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMyUser))
            )
            .andExpect(status().isOk());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeUpdate);
        MyUser testMyUser = myUserList.get(myUserList.size() - 1);
        assertThat(testMyUser.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testMyUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testMyUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testMyUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testMyUser.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testMyUser.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testMyUser.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void patchNonExistingMyUser() throws Exception {
        int databaseSizeBeforeUpdate = myUserRepository.findAll().size();
        myUser.setId(count.incrementAndGet());

        // Create the MyUser
        MyUserDTO myUserDTO = myUserMapper.toDto(myUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMyUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, myUserDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(myUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMyUser() throws Exception {
        int databaseSizeBeforeUpdate = myUserRepository.findAll().size();
        myUser.setId(count.incrementAndGet());

        // Create the MyUser
        MyUserDTO myUserDTO = myUserMapper.toDto(myUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMyUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(myUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMyUser() throws Exception {
        int databaseSizeBeforeUpdate = myUserRepository.findAll().size();
        myUser.setId(count.incrementAndGet());

        // Create the MyUser
        MyUserDTO myUserDTO = myUserMapper.toDto(myUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMyUserMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(myUserDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMyUser() throws Exception {
        // Initialize the database
        myUserRepository.saveAndFlush(myUser);

        int databaseSizeBeforeDelete = myUserRepository.findAll().size();

        // Delete the myUser
        restMyUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, myUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
