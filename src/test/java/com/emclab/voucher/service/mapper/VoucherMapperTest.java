package com.emclab.voucher.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VoucherMapperTest {

    private VoucherMapper voucherMapper;

    @BeforeEach
    public void setUp() {
        voucherMapper = new VoucherMapperImpl();
    }
}
