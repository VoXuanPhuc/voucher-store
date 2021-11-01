package com.emclab.voucher.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.emclab.voucher.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StoreUserDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StoreUserDTO.class);
        StoreUserDTO storeUserDTO1 = new StoreUserDTO();
        storeUserDTO1.setId(1L);
        StoreUserDTO storeUserDTO2 = new StoreUserDTO();
        assertThat(storeUserDTO1).isNotEqualTo(storeUserDTO2);
        storeUserDTO2.setId(storeUserDTO1.getId());
        assertThat(storeUserDTO1).isEqualTo(storeUserDTO2);
        storeUserDTO2.setId(2L);
        assertThat(storeUserDTO1).isNotEqualTo(storeUserDTO2);
        storeUserDTO1.setId(null);
        assertThat(storeUserDTO1).isNotEqualTo(storeUserDTO2);
    }
}
