package com.emclab.voucher.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.emclab.voucher.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VoucherCodeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoucherCode.class);
        VoucherCode voucherCode1 = new VoucherCode();
        voucherCode1.setId(1L);
        VoucherCode voucherCode2 = new VoucherCode();
        voucherCode2.setId(voucherCode1.getId());
        assertThat(voucherCode1).isEqualTo(voucherCode2);
        voucherCode2.setId(2L);
        assertThat(voucherCode1).isNotEqualTo(voucherCode2);
        voucherCode1.setId(null);
        assertThat(voucherCode1).isNotEqualTo(voucherCode2);
    }
}
