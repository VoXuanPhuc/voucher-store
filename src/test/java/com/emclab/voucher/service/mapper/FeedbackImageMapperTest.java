package com.emclab.voucher.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FeedbackImageMapperTest {

    private FeedbackImageMapper feedbackImageMapper;

    @BeforeEach
    public void setUp() {
        feedbackImageMapper = new FeedbackImageMapperImpl();
    }
}
