package com.emclab.voucher.repository;

import com.emclab.voucher.domain.Feedback;
import com.emclab.voucher.domain.Voucher;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Feedback entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Page<Feedback> findByVoucherAndRate(Voucher voucher, Integer rate, Pageable pageable);

    Page<Feedback> findByVoucher(Voucher voucher, Pageable pageable);

    List<Feedback> findByVoucher(Voucher voucher);

    List<Feedback> findByVoucherAndRate(Voucher voucher, int rate);
}
