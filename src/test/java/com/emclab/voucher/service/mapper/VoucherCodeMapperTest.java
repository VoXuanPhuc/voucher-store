package com.emclab.voucher.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VoucherCodeMapperTest {

    private VoucherCodeMapper voucherCodeMapper;

    @BeforeEach
    public void setUp() {
        voucherCodeMapper = new VoucherCodeMapperImpl();
    }
}
