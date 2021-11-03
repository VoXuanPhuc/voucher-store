package com.emclab.voucher.repository;

import com.emclab.voucher.domain.MyOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MyOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MyOrderRepository extends JpaRepository<MyOrder, Long> {}
