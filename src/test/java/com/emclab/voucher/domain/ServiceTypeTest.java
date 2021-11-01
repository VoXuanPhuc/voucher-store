package com.emclab.voucher.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.emclab.voucher.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServiceTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceType.class);
        ServiceType serviceType1 = new ServiceType();
        serviceType1.setId(1L);
        ServiceType serviceType2 = new ServiceType();
        serviceType2.setId(serviceType1.getId());
        assertThat(serviceType1).isEqualTo(serviceType2);
        serviceType2.setId(2L);
        assertThat(serviceType1).isNotEqualTo(serviceType2);
        serviceType1.setId(null);
        assertThat(serviceType1).isNotEqualTo(serviceType2);
    }
}
