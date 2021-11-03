package com.emclab.voucher.repository;

import com.emclab.voucher.domain.VoucherCode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the VoucherCode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoucherCodeRepository extends JpaRepository<VoucherCode, Long> {}
