package com.emclab.voucher.repository;

import com.emclab.voucher.domain.MyOrder;
import com.emclab.voucher.domain.Voucher;
import com.emclab.voucher.domain.VoucherCode;
import com.emclab.voucher.domain.VoucherStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the VoucherCode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoucherCodeRepository extends JpaRepository<VoucherCode, Long> {
    long countByVoucherAndStatus(Voucher voucher, VoucherStatus voucherStatus);

    List<VoucherCode> findByOrder(MyOrder order);
}
