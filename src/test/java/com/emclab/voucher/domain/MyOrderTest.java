package com.emclab.voucher.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.emclab.voucher.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MyOrderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MyOrder.class);
        MyOrder myOrder1 = new MyOrder();
        myOrder1.setId(1L);
        MyOrder myOrder2 = new MyOrder();
        myOrder2.setId(myOrder1.getId());
        assertThat(myOrder1).isEqualTo(myOrder2);
        myOrder2.setId(2L);
        assertThat(myOrder1).isNotEqualTo(myOrder2);
        myOrder1.setId(null);
        assertThat(myOrder1).isNotEqualTo(myOrder2);
    }
}
