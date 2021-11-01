package com.emclab.voucher.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.emclab.voucher.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VoucherCodeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoucherCodeDTO.class);
        VoucherCodeDTO voucherCodeDTO1 = new VoucherCodeDTO();
        voucherCodeDTO1.setId(1L);
        VoucherCodeDTO voucherCodeDTO2 = new VoucherCodeDTO();
        assertThat(voucherCodeDTO1).isNotEqualTo(voucherCodeDTO2);
        voucherCodeDTO2.setId(voucherCodeDTO1.getId());
        assertThat(voucherCodeDTO1).isEqualTo(voucherCodeDTO2);
        voucherCodeDTO2.setId(2L);
        assertThat(voucherCodeDTO1).isNotEqualTo(voucherCodeDTO2);
        voucherCodeDTO1.setId(null);
        assertThat(voucherCodeDTO1).isNotEqualTo(voucherCodeDTO2);
    }
}
