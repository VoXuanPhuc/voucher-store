package com.emclab.voucher.repository;

import com.emclab.voucher.domain.MyOrder;
import com.emclab.voucher.domain.MyUser;
import com.emclab.voucher.domain.OrderStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MyOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MyOrderRepository extends JpaRepository<MyOrder, Long> {
    List<MyOrder> findByUserAndStatus(MyUser user, OrderStatus status);
}
