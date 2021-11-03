package com.emclab.voucher.repository;

import com.emclab.voucher.domain.StoreUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the StoreUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StoreUserRepository extends JpaRepository<StoreUser, Long> {}
