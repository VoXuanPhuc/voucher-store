package com.emclab.voucher.repository;

import com.emclab.voucher.domain.BenifitPackage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BenifitPackage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BenifitPackageRepository extends JpaRepository<BenifitPackage, Long> {}
