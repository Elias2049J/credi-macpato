package com.runaitec.credimacpato.repository;

import com.runaitec.credimacpato.entity.PaymentState;
import com.runaitec.credimacpato.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Integer> {

    List<Voucher> findByStand_IdAndIssueDateTimeBetween(Long standId, LocalDateTime from, LocalDateTime to);

    List<Voucher> findByCustomer_IdAndStateNot(Long customerId, PaymentState state);

    @Query("select v from Voucher v join fetch v.voucherItems where v.stand.id = :standId and v.issueDateTime between :from and :to")
    List<Voucher> findWithItemsByStandAndIssueDateTimeBetween(@Param("standId") Long standId,
                                                             @Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

    @Query("select distinct v from Voucher v join fetch v.voucherItems where v.customer.id = :customerId and v.state <> :state")
    List<Voucher> findWithItemsByCustomerIdAndStateNot(@Param("customerId") Long customerId, @Param("state") PaymentState state);
}
