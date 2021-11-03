package com.emclab.voucher.repository;

import com.emclab.voucher.domain.RelationshipType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RelationshipType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RelationshipTypeRepository extends JpaRepository<RelationshipType, Long> {}
