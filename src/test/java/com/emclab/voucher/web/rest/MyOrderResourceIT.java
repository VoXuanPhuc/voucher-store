package com.emclab.voucher.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.emclab.voucher.IntegrationTest;
import com.emclab.voucher.domain.MyOrder;
import com.emclab.voucher.repository.MyOrderRepository;
import com.emclab.voucher.service.dto.MyOrderDTO;
import com.emclab.voucher.service.mapper.MyOrderMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link MyOrderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MyOrderResourceIT {

    private static final Double DEFAULT_TOTAL_COST = 1D;
    private static final Double UPDATED_TOTAL_COST = 2D;

    private static final Instant DEFAULT_PAYMENT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PAYMENT_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/my-orders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MyOrderRepository myOrderRepository;

    @Autowired
    private MyOrderMapper myOrderMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMyOrderMockMvc;

    private MyOrder myOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyOrder createEntity(EntityManager em) {
        MyOrder myOrder = new MyOrder().totalCost(DEFAULT_TOTAL_COST).paymentTime(DEFAULT_PAYMENT_TIME);
        return myOrder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyOrder createUpdatedEntity(EntityManager em) {
        MyOrder myOrder = new MyOrder().totalCost(UPDATED_TOTAL_COST).paymentTime(UPDATED_PAYMENT_TIME);
        return myOrder;
    }

    @BeforeEach
    public void initTest() {
        myOrder = createEntity(em);
    }

    @Test
    @Transactional
    void createMyOrder() throws Exception {
        int databaseSizeBeforeCreate = myOrderRepository.findAll().size();
        // Create the MyOrder
        MyOrderDTO myOrderDTO = myOrderMapper.toDto(myOrder);
        restMyOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the MyOrder in the database
        List<MyOrder> myOrderList = myOrderRepository.findAll();
        assertThat(myOrderList).hasSize(databaseSizeBeforeCreate + 1);
        MyOrder testMyOrder = myOrderList.get(myOrderList.size() - 1);
        assertThat(testMyOrder.getTotalCost()).isEqualTo(DEFAULT_TOTAL_COST);
        assertThat(testMyOrder.getPaymentTime()).isEqualTo(DEFAULT_PAYMENT_TIME);
    }

    @Test
    @Transactional
    void createMyOrderWithExistingId() throws Exception {
        // Create the MyOrder with an existing ID
        myOrder.setId(1L);
        MyOrderDTO myOrderDTO = myOrderMapper.toDto(myOrder);

        int databaseSizeBeforeCreate = myOrderRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMyOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MyOrder in the database
        List<MyOrder> myOrderList = myOrderRepository.findAll();
        assertThat(myOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTotalCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = myOrderRepository.findAll().size();
        // set the field null
        myOrder.setTotalCost(null);

        // Create the MyOrder, which fails.
        MyOrderDTO myOrderDTO = myOrderMapper.toDto(myOrder);

        restMyOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myOrderDTO)))
            .andExpect(status().isBadRequest());

        List<MyOrder> myOrderList = myOrderRepository.findAll();
        assertThat(myOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaymentTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = myOrderRepository.findAll().size();
        // set the field null
        myOrder.setPaymentTime(null);

        // Create the MyOrder, which fails.
        MyOrderDTO myOrderDTO = myOrderMapper.toDto(myOrder);

        restMyOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myOrderDTO)))
            .andExpect(status().isBadRequest());

        List<MyOrder> myOrderList = myOrderRepository.findAll();
        assertThat(myOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMyOrders() throws Exception {
        // Initialize the database
        myOrderRepository.saveAndFlush(myOrder);

        // Get all the myOrderList
        restMyOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(myOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalCost").value(hasItem(DEFAULT_TOTAL_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentTime").value(hasItem(DEFAULT_PAYMENT_TIME.toString())));
    }

    @Test
    @Transactional
    void getMyOrder() throws Exception {
        // Initialize the database
        myOrderRepository.saveAndFlush(myOrder);

        // Get the myOrder
        restMyOrderMockMvc
            .perform(get(ENTITY_API_URL_ID, myOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(myOrder.getId().intValue()))
            .andExpect(jsonPath("$.totalCost").value(DEFAULT_TOTAL_COST.doubleValue()))
            .andExpect(jsonPath("$.paymentTime").value(DEFAULT_PAYMENT_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMyOrder() throws Exception {
        // Get the myOrder
        restMyOrderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMyOrder() throws Exception {
        // Initialize the database
        myOrderRepository.saveAndFlush(myOrder);

        int databaseSizeBeforeUpdate = myOrderRepository.findAll().size();

        // Update the myOrder
        MyOrder updatedMyOrder = myOrderRepository.findById(myOrder.getId()).get();
        // Disconnect from session so that the updates on updatedMyOrder are not directly saved in db
        em.detach(updatedMyOrder);
        updatedMyOrder.totalCost(UPDATED_TOTAL_COST).paymentTime(UPDATED_PAYMENT_TIME);
        MyOrderDTO myOrderDTO = myOrderMapper.toDto(updatedMyOrder);

        restMyOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, myOrderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(myOrderDTO))
            )
            .andExpect(status().isOk());

        // Validate the MyOrder in the database
        List<MyOrder> myOrderList = myOrderRepository.findAll();
        assertThat(myOrderList).hasSize(databaseSizeBeforeUpdate);
        MyOrder testMyOrder = myOrderList.get(myOrderList.size() - 1);
        assertThat(testMyOrder.getTotalCost()).isEqualTo(UPDATED_TOTAL_COST);
        assertThat(testMyOrder.getPaymentTime()).isEqualTo(UPDATED_PAYMENT_TIME);
    }

    @Test
    @Transactional
    void putNonExistingMyOrder() throws Exception {
        int databaseSizeBeforeUpdate = myOrderRepository.findAll().size();
        myOrder.setId(count.incrementAndGet());

        // Create the MyOrder
        MyOrderDTO myOrderDTO = myOrderMapper.toDto(myOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMyOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, myOrderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(myOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MyOrder in the database
        List<MyOrder> myOrderList = myOrderRepository.findAll();
        assertThat(myOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMyOrder() throws Exception {
        int databaseSizeBeforeUpdate = myOrderRepository.findAll().size();
        myOrder.setId(count.incrementAndGet());

        // Create the MyOrder
        MyOrderDTO myOrderDTO = myOrderMapper.toDto(myOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMyOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(myOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MyOrder in the database
        List<MyOrder> myOrderList = myOrderRepository.findAll();
        assertThat(myOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMyOrder() throws Exception {
        int databaseSizeBeforeUpdate = myOrderRepository.findAll().size();
        myOrder.setId(count.incrementAndGet());

        // Create the MyOrder
        MyOrderDTO myOrderDTO = myOrderMapper.toDto(myOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMyOrderMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myOrderDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MyOrder in the database
        List<MyOrder> myOrderList = myOrderRepository.findAll();
        assertThat(myOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMyOrderWithPatch() throws Exception {
        // Initialize the database
        myOrderRepository.saveAndFlush(myOrder);

        int databaseSizeBeforeUpdate = myOrderRepository.findAll().size();

        // Update the myOrder using partial update
        MyOrder partialUpdatedMyOrder = new MyOrder();
        partialUpdatedMyOrder.setId(myOrder.getId());

        restMyOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMyOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMyOrder))
            )
            .andExpect(status().isOk());

        // Validate the MyOrder in the database
        List<MyOrder> myOrderList = myOrderRepository.findAll();
        assertThat(myOrderList).hasSize(databaseSizeBeforeUpdate);
        MyOrder testMyOrder = myOrderList.get(myOrderList.size() - 1);
        assertThat(testMyOrder.getTotalCost()).isEqualTo(DEFAULT_TOTAL_COST);
        assertThat(testMyOrder.getPaymentTime()).isEqualTo(DEFAULT_PAYMENT_TIME);
    }

    @Test
    @Transactional
    void fullUpdateMyOrderWithPatch() throws Exception {
        // Initialize the database
        myOrderRepository.saveAndFlush(myOrder);

        int databaseSizeBeforeUpdate = myOrderRepository.findAll().size();

        // Update the myOrder using partial update
        MyOrder partialUpdatedMyOrder = new MyOrder();
        partialUpdatedMyOrder.setId(myOrder.getId());

        partialUpdatedMyOrder.totalCost(UPDATED_TOTAL_COST).paymentTime(UPDATED_PAYMENT_TIME);

        restMyOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMyOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMyOrder))
            )
            .andExpect(status().isOk());

        // Validate the MyOrder in the database
        List<MyOrder> myOrderList = myOrderRepository.findAll();
        assertThat(myOrderList).hasSize(databaseSizeBeforeUpdate);
        MyOrder testMyOrder = myOrderList.get(myOrderList.size() - 1);
        assertThat(testMyOrder.getTotalCost()).isEqualTo(UPDATED_TOTAL_COST);
        assertThat(testMyOrder.getPaymentTime()).isEqualTo(UPDATED_PAYMENT_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingMyOrder() throws Exception {
        int databaseSizeBeforeUpdate = myOrderRepository.findAll().size();
        myOrder.setId(count.incrementAndGet());

        // Create the MyOrder
        MyOrderDTO myOrderDTO = myOrderMapper.toDto(myOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMyOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, myOrderDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(myOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MyOrder in the database
        List<MyOrder> myOrderList = myOrderRepository.findAll();
        assertThat(myOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMyOrder() throws Exception {
        int databaseSizeBeforeUpdate = myOrderRepository.findAll().size();
        myOrder.setId(count.incrementAndGet());

        // Create the MyOrder
        MyOrderDTO myOrderDTO = myOrderMapper.toDto(myOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMyOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(myOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MyOrder in the database
        List<MyOrder> myOrderList = myOrderRepository.findAll();
        assertThat(myOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMyOrder() throws Exception {
        int databaseSizeBeforeUpdate = myOrderRepository.findAll().size();
        myOrder.setId(count.incrementAndGet());

        // Create the MyOrder
        MyOrderDTO myOrderDTO = myOrderMapper.toDto(myOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMyOrderMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(myOrderDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MyOrder in the database
        List<MyOrder> myOrderList = myOrderRepository.findAll();
        assertThat(myOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMyOrder() throws Exception {
        // Initialize the database
        myOrderRepository.saveAndFlush(myOrder);

        int databaseSizeBeforeDelete = myOrderRepository.findAll().size();

        // Delete the myOrder
        restMyOrderMockMvc
            .perform(delete(ENTITY_API_URL_ID, myOrder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MyOrder> myOrderList = myOrderRepository.findAll();
        assertThat(myOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
