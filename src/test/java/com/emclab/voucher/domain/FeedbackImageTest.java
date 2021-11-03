package com.emclab.voucher.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.emclab.voucher.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FeedbackImageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeedbackImage.class);
        FeedbackImage feedbackImage1 = new FeedbackImage();
        feedbackImage1.setId(1L);
        FeedbackImage feedbackImage2 = new FeedbackImage();
        feedbackImage2.setId(feedbackImage1.getId());
        assertThat(feedbackImage1).isEqualTo(feedbackImage2);
        feedbackImage2.setId(2L);
        assertThat(feedbackImage1).isNotEqualTo(feedbackImage2);
        feedbackImage1.setId(null);
        assertThat(feedbackImage1).isNotEqualTo(feedbackImage2);
    }
}
