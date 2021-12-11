package com.emclab.voucher.repository;

import com.emclab.voucher.domain.ServiceType;
import com.emclab.voucher.domain.Voucher;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Voucher entity.
 */
@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    @Query(
        value = "select distinct voucher from Voucher voucher left join fetch voucher.products",
        countQuery = "select count(distinct voucher) from Voucher voucher"
    )
    Page<Voucher> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct voucher from Voucher voucher left join fetch voucher.products")
    List<Voucher> findAllWithEagerRelationships();

    @Query("select voucher from Voucher voucher left join fetch voucher.products where voucher.id =:id")
    Optional<Voucher> findOneWithEagerRelationships(@Param("id") Long id);

    List<Voucher> findByType(ServiceType type);

    List<Voucher> findByType(ServiceType type, Pageable pageable);
}
