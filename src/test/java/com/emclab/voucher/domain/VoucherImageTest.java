package com.emclab.voucher.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.emclab.voucher.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VoucherImageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoucherImage.class);
        VoucherImage voucherImage1 = new VoucherImage();
        voucherImage1.setId(1L);
        VoucherImage voucherImage2 = new VoucherImage();
        voucherImage2.setId(voucherImage1.getId());
        assertThat(voucherImage1).isEqualTo(voucherImage2);
        voucherImage2.setId(2L);
        assertThat(voucherImage1).isNotEqualTo(voucherImage2);
        voucherImage1.setId(null);
        assertThat(voucherImage1).isNotEqualTo(voucherImage2);
    }
}
