package com.runaitec.credimacpato.repository;

import com.runaitec.credimacpato.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByCustomer_Id(Long customerId);
    List<Payment> findByVoucher_Id(Integer voucherId);
    List<Payment> findByVoucher_Stand_IdAndDateTimeBetween(Long standId, LocalDateTime from, LocalDateTime to);
}
