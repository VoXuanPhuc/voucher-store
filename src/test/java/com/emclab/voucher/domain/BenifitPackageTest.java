package com.emclab.voucher.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.emclab.voucher.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BenifitPackageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BenifitPackage.class);
        BenifitPackage benifitPackage1 = new BenifitPackage();
        benifitPackage1.setId(1L);
        BenifitPackage benifitPackage2 = new BenifitPackage();
        benifitPackage2.setId(benifitPackage1.getId());
        assertThat(benifitPackage1).isEqualTo(benifitPackage2);
        benifitPackage2.setId(2L);
        assertThat(benifitPackage1).isNotEqualTo(benifitPackage2);
        benifitPackage1.setId(null);
        assertThat(benifitPackage1).isNotEqualTo(benifitPackage2);
    }
}
