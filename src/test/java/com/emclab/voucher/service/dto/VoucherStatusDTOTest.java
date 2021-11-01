package com.emclab.voucher.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.emclab.voucher.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VoucherStatusDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoucherStatusDTO.class);
        VoucherStatusDTO voucherStatusDTO1 = new VoucherStatusDTO();
        voucherStatusDTO1.setId(1L);
        VoucherStatusDTO voucherStatusDTO2 = new VoucherStatusDTO();
        assertThat(voucherStatusDTO1).isNotEqualTo(voucherStatusDTO2);
        voucherStatusDTO2.setId(voucherStatusDTO1.getId());
        assertThat(voucherStatusDTO1).isEqualTo(voucherStatusDTO2);
        voucherStatusDTO2.setId(2L);
        assertThat(voucherStatusDTO1).isNotEqualTo(voucherStatusDTO2);
        voucherStatusDTO1.setId(null);
        assertThat(voucherStatusDTO1).isNotEqualTo(voucherStatusDTO2);
    }
}
