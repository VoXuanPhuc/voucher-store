package com.emclab.voucher.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BenifitPackageMapperTest {

    private BenifitPackageMapper benifitPackageMapper;

    @BeforeEach
    public void setUp() {
        benifitPackageMapper = new BenifitPackageMapperImpl();
    }
}
