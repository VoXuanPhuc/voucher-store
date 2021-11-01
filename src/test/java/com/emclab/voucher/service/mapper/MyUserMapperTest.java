package com.emclab.voucher.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyUserMapperTest {

    private MyUserMapper myUserMapper;

    @BeforeEach
    public void setUp() {
        myUserMapper = new MyUserMapperImpl();
    }
}
