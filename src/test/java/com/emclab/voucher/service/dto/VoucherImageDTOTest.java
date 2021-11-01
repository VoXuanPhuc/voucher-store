package com.emclab.voucher.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.emclab.voucher.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VoucherImageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoucherImageDTO.class);
        VoucherImageDTO voucherImageDTO1 = new VoucherImageDTO();
        voucherImageDTO1.setId(1L);
        VoucherImageDTO voucherImageDTO2 = new VoucherImageDTO();
        assertThat(voucherImageDTO1).isNotEqualTo(voucherImageDTO2);
        voucherImageDTO2.setId(voucherImageDTO1.getId());
        assertThat(voucherImageDTO1).isEqualTo(voucherImageDTO2);
        voucherImageDTO2.setId(2L);
        assertThat(voucherImageDTO1).isNotEqualTo(voucherImageDTO2);
        voucherImageDTO1.setId(null);
        assertThat(voucherImageDTO1).isNotEqualTo(voucherImageDTO2);
    }
}
