package com.emclab.voucher.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.emclab.voucher.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FeedbackImageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeedbackImageDTO.class);
        FeedbackImageDTO feedbackImageDTO1 = new FeedbackImageDTO();
        feedbackImageDTO1.setId(1L);
        FeedbackImageDTO feedbackImageDTO2 = new FeedbackImageDTO();
        assertThat(feedbackImageDTO1).isNotEqualTo(feedbackImageDTO2);
        feedbackImageDTO2.setId(feedbackImageDTO1.getId());
        assertThat(feedbackImageDTO1).isEqualTo(feedbackImageDTO2);
        feedbackImageDTO2.setId(2L);
        assertThat(feedbackImageDTO1).isNotEqualTo(feedbackImageDTO2);
        feedbackImageDTO1.setId(null);
        assertThat(feedbackImageDTO1).isNotEqualTo(feedbackImageDTO2);
    }
}
