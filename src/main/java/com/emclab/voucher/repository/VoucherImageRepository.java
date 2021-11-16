package com.emclab.voucher.repository;

import com.emclab.voucher.domain.Voucher;
import com.emclab.voucher.domain.VoucherImage;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the VoucherImage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoucherImageRepository extends JpaRepository<VoucherImage, Long> {
    List<VoucherImage> findByVoucher(Voucher voucher);
}
