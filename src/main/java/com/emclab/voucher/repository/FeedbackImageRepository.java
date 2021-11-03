package com.emclab.voucher.repository;

import com.emclab.voucher.domain.FeedbackImage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FeedbackImage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeedbackImageRepository extends JpaRepository<FeedbackImage, Long> {}
