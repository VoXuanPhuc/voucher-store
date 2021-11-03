package com.emclab.voucher.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StoreUserMapperTest {

    private StoreUserMapper storeUserMapper;

    @BeforeEach
    public void setUp() {
        storeUserMapper = new StoreUserMapperImpl();
    }
}
