package com.runaitec.credimacpato.repository;

import com.runaitec.credimacpato.entity.PaymentState;
import com.runaitec.credimacpato.entity.Voucher;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {

    @EntityGraph(attributePaths = {"voucherItems", "voucherItems.charge"})
    List<Voucher> findAllByStand_IdAndIssueDateBetween(Long standId, LocalDate from, LocalDate to);

    @EntityGraph(attributePaths = {"voucherItems", "voucherItems.charge"})
    List<Voucher> findAllByCustomer_IdAndStateNot(Long customerId, PaymentState state);

    @EntityGraph(attributePaths = {"voucherItems", "voucherItems.charge"})
    List<Voucher> findAllByCustomer_IdAndState(Long customerId, PaymentState state);

    List<Voucher> findAllByCustomer_Id(Long customerId);

    List<Voucher> findAllByStand_Id(Long standId);

    List<Voucher> findAllByIssuer_IdAndState(Long issuerId, PaymentState state);


    long countAllByIssuerIdAndIssueDateBetween(Long issuerId, LocalDate issueDateAfter, LocalDate issueDateBefore);

    long countAllByIssuer_IdAndIssueDate(Long issuerId, LocalDate issueDate);

    @EntityGraph(attributePaths = {"voucherItems", "voucherItems.charge"})
    List<Voucher> findAllByStand_IdAndIssuer_IdAndIssueDate(Long standId, Long issuerId, LocalDate issueDate);

    Voucher findTopBySerialNumberStartingWithOrderBySerialNumberDesc(String prefix);
}
