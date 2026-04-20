package com.runaitec.credimacpato.repository;

import com.runaitec.credimacpato.entity.Payment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByCustomer_Id(Long customerId);

    List<Payment> findAllByVoucher_Id(Long voucherId);

    @EntityGraph(attributePaths = {"paidItems", "paidItems.voucher", "paidItems.charge"})
    List<Payment> findAllByVoucher_Stand_IdAndDateTimeBetween(Long standId, LocalDateTime from, LocalDateTime to);
}
