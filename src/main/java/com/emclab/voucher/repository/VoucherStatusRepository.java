package com.emclab.voucher.repository;

import com.emclab.voucher.domain.VoucherStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the VoucherStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoucherStatusRepository extends JpaRepository<VoucherStatus, Long> {}
