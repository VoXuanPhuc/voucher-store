package com.emclab.voucher.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderStatusMapperTest {

    private OrderStatusMapper orderStatusMapper;

    @BeforeEach
    public void setUp() {
        orderStatusMapper = new OrderStatusMapperImpl();
    }
}
