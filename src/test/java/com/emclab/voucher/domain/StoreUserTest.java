package com.emclab.voucher.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.emclab.voucher.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StoreUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StoreUser.class);
        StoreUser storeUser1 = new StoreUser();
        storeUser1.setId(1L);
        StoreUser storeUser2 = new StoreUser();
        storeUser2.setId(storeUser1.getId());
        assertThat(storeUser1).isEqualTo(storeUser2);
        storeUser2.setId(2L);
        assertThat(storeUser1).isNotEqualTo(storeUser2);
        storeUser1.setId(null);
        assertThat(storeUser1).isNotEqualTo(storeUser2);
    }
}
