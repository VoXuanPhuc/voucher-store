package com.emclab.voucher.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.emclab.voucher.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BenifitPackageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BenifitPackageDTO.class);
        BenifitPackageDTO benifitPackageDTO1 = new BenifitPackageDTO();
        benifitPackageDTO1.setId(1L);
        BenifitPackageDTO benifitPackageDTO2 = new BenifitPackageDTO();
        assertThat(benifitPackageDTO1).isNotEqualTo(benifitPackageDTO2);
        benifitPackageDTO2.setId(benifitPackageDTO1.getId());
        assertThat(benifitPackageDTO1).isEqualTo(benifitPackageDTO2);
        benifitPackageDTO2.setId(2L);
        assertThat(benifitPackageDTO1).isNotEqualTo(benifitPackageDTO2);
        benifitPackageDTO1.setId(null);
        assertThat(benifitPackageDTO1).isNotEqualTo(benifitPackageDTO2);
    }
}
