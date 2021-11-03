package com.emclab.voucher.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.emclab.voucher.IntegrationTest;
import com.emclab.voucher.domain.OrderStatus;
import com.emclab.voucher.repository.OrderStatusRepository;
import com.emclab.voucher.service.dto.OrderStatusDTO;
import com.emclab.voucher.service.mapper.OrderStatusMapper;
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
 * Integration tests for the {@link OrderStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrderStatusResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/order-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderStatusMockMvc;

    private OrderStatus orderStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderStatus createEntity(EntityManager em) {
        OrderStatus orderStatus = new OrderStatus().name(DEFAULT_NAME);
        return orderStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderStatus createUpdatedEntity(EntityManager em) {
        OrderStatus orderStatus = new OrderStatus().name(UPDATED_NAME);
        return orderStatus;
    }

    @BeforeEach
    public void initTest() {
        orderStatus = createEntity(em);
    }

    @Test
    @Transactional
    void createOrderStatus() throws Exception {
        int databaseSizeBeforeCreate = orderStatusRepository.findAll().size();
        // Create the OrderStatus
        OrderStatusDTO orderStatusDTO = orderStatusMapper.toDto(orderStatus);
        restOrderStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderStatusDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrderStatus in the database
        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        assertThat(orderStatusList).hasSize(databaseSizeBeforeCreate + 1);
        OrderStatus testOrderStatus = orderStatusList.get(orderStatusList.size() - 1);
        assertThat(testOrderStatus.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createOrderStatusWithExistingId() throws Exception {
        // Create the OrderStatus with an existing ID
        orderStatus.setId(1L);
        OrderStatusDTO orderStatusDTO = orderStatusMapper.toDto(orderStatus);

        int databaseSizeBeforeCreate = orderStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderStatus in the database
        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        assertThat(orderStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderStatusRepository.findAll().size();
        // set the field null
        orderStatus.setName(null);

        // Create the OrderStatus, which fails.
        OrderStatusDTO orderStatusDTO = orderStatusMapper.toDto(orderStatus);

        restOrderStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderStatusDTO))
            )
            .andExpect(status().isBadRequest());

        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        assertThat(orderStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrderStatuses() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList
        restOrderStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getOrderStatus() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get the orderStatus
        restOrderStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, orderStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingOrderStatus() throws Exception {
        // Get the orderStatus
        restOrderStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrderStatus() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        int databaseSizeBeforeUpdate = orderStatusRepository.findAll().size();

        // Update the orderStatus
        OrderStatus updatedOrderStatus = orderStatusRepository.findById(orderStatus.getId()).get();
        // Disconnect from session so that the updates on updatedOrderStatus are not directly saved in db
        em.detach(updatedOrderStatus);
        updatedOrderStatus.name(UPDATED_NAME);
        OrderStatusDTO orderStatusDTO = orderStatusMapper.toDto(updatedOrderStatus);

        restOrderStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderStatusDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrderStatus in the database
        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        assertThat(orderStatusList).hasSize(databaseSizeBeforeUpdate);
        OrderStatus testOrderStatus = orderStatusList.get(orderStatusList.size() - 1);
        assertThat(testOrderStatus.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingOrderStatus() throws Exception {
        int databaseSizeBeforeUpdate = orderStatusRepository.findAll().size();
        orderStatus.setId(count.incrementAndGet());

        // Create the OrderStatus
        OrderStatusDTO orderStatusDTO = orderStatusMapper.toDto(orderStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderStatus in the database
        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        assertThat(orderStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrderStatus() throws Exception {
        int databaseSizeBeforeUpdate = orderStatusRepository.findAll().size();
        orderStatus.setId(count.incrementAndGet());

        // Create the OrderStatus
        OrderStatusDTO orderStatusDTO = orderStatusMapper.toDto(orderStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderStatus in the database
        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        assertThat(orderStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrderStatus() throws Exception {
        int databaseSizeBeforeUpdate = orderStatusRepository.findAll().size();
        orderStatus.setId(count.incrementAndGet());

        // Create the OrderStatus
        OrderStatusDTO orderStatusDTO = orderStatusMapper.toDto(orderStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderStatusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderStatusDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderStatus in the database
        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        assertThat(orderStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrderStatusWithPatch() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        int databaseSizeBeforeUpdate = orderStatusRepository.findAll().size();

        // Update the orderStatus using partial update
        OrderStatus partialUpdatedOrderStatus = new OrderStatus();
        partialUpdatedOrderStatus.setId(orderStatus.getId());

        partialUpdatedOrderStatus.name(UPDATED_NAME);

        restOrderStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderStatus))
            )
            .andExpect(status().isOk());

        // Validate the OrderStatus in the database
        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        assertThat(orderStatusList).hasSize(databaseSizeBeforeUpdate);
        OrderStatus testOrderStatus = orderStatusList.get(orderStatusList.size() - 1);
        assertThat(testOrderStatus.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateOrderStatusWithPatch() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        int databaseSizeBeforeUpdate = orderStatusRepository.findAll().size();

        // Update the orderStatus using partial update
        OrderStatus partialUpdatedOrderStatus = new OrderStatus();
        partialUpdatedOrderStatus.setId(orderStatus.getId());

        partialUpdatedOrderStatus.name(UPDATED_NAME);

        restOrderStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderStatus))
            )
            .andExpect(status().isOk());

        // Validate the OrderStatus in the database
        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        assertThat(orderStatusList).hasSize(databaseSizeBeforeUpdate);
        OrderStatus testOrderStatus = orderStatusList.get(orderStatusList.size() - 1);
        assertThat(testOrderStatus.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingOrderStatus() throws Exception {
        int databaseSizeBeforeUpdate = orderStatusRepository.findAll().size();
        orderStatus.setId(count.incrementAndGet());

        // Create the OrderStatus
        OrderStatusDTO orderStatusDTO = orderStatusMapper.toDto(orderStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orderStatusDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderStatus in the database
        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        assertThat(orderStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrderStatus() throws Exception {
        int databaseSizeBeforeUpdate = orderStatusRepository.findAll().size();
        orderStatus.setId(count.incrementAndGet());

        // Create the OrderStatus
        OrderStatusDTO orderStatusDTO = orderStatusMapper.toDto(orderStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderStatus in the database
        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        assertThat(orderStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrderStatus() throws Exception {
        int databaseSizeBeforeUpdate = orderStatusRepository.findAll().size();
        orderStatus.setId(count.incrementAndGet());

        // Create the OrderStatus
        OrderStatusDTO orderStatusDTO = orderStatusMapper.toDto(orderStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderStatusMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(orderStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderStatus in the database
        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        assertThat(orderStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrderStatus() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        int databaseSizeBeforeDelete = orderStatusRepository.findAll().size();

        // Delete the orderStatus
        restOrderStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, orderStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        assertThat(orderStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
