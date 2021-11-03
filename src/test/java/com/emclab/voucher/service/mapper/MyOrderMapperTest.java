package com.emclab.voucher.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyOrderMapperTest {

    private MyOrderMapper myOrderMapper;

    @BeforeEach
    public void setUp() {
        myOrderMapper = new MyOrderMapperImpl();
    }
}
