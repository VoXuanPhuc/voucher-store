package com.emclab.voucher.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.emclab.voucher.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VoucherStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoucherStatus.class);
        VoucherStatus voucherStatus1 = new VoucherStatus();
        voucherStatus1.setId(1L);
        VoucherStatus voucherStatus2 = new VoucherStatus();
        voucherStatus2.setId(voucherStatus1.getId());
        assertThat(voucherStatus1).isEqualTo(voucherStatus2);
        voucherStatus2.setId(2L);
        assertThat(voucherStatus1).isNotEqualTo(voucherStatus2);
        voucherStatus1.setId(null);
        assertThat(voucherStatus1).isNotEqualTo(voucherStatus2);
    }
}
