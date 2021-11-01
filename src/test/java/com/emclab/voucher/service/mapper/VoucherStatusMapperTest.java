package com.emclab.voucher.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VoucherStatusMapperTest {

    private VoucherStatusMapper voucherStatusMapper;

    @BeforeEach
    public void setUp() {
        voucherStatusMapper = new VoucherStatusMapperImpl();
    }
}
