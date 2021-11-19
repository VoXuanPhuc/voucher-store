package com.emclab.voucher.repository;

import com.emclab.voucher.domain.MyUser;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MyUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    // String USERS_BY_LOGIN_CACHE = "usersByLogin";

    // @EntityGraph(attributePaths = "authorities")
    // @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
    Optional<MyUser> findByUsername(String username);
}
