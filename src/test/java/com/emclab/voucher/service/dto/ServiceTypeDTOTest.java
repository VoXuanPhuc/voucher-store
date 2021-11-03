package com.emclab.voucher.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.emclab.voucher.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServiceTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceTypeDTO.class);
        ServiceTypeDTO serviceTypeDTO1 = new ServiceTypeDTO();
        serviceTypeDTO1.setId(1L);
        ServiceTypeDTO serviceTypeDTO2 = new ServiceTypeDTO();
        assertThat(serviceTypeDTO1).isNotEqualTo(serviceTypeDTO2);
        serviceTypeDTO2.setId(serviceTypeDTO1.getId());
        assertThat(serviceTypeDTO1).isEqualTo(serviceTypeDTO2);
        serviceTypeDTO2.setId(2L);
        assertThat(serviceTypeDTO1).isNotEqualTo(serviceTypeDTO2);
        serviceTypeDTO1.setId(null);
        assertThat(serviceTypeDTO1).isNotEqualTo(serviceTypeDTO2);
    }
}
