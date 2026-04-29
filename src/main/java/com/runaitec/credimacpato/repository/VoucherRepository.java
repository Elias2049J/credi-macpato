package com.runaitec.credimacpato.repository;

import com.runaitec.credimacpato.entity.PaymentState;
import com.runaitec.credimacpato.entity.Voucher;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {

    @EntityGraph(attributePaths = {"voucherItems", "voucherItems.charge", "stand", "customer"})
    List<Voucher> findAllByStand_IdAndIssueDateBetween(Long standId, LocalDate from, LocalDate to);

    @EntityGraph(attributePaths = {"voucherItems", "voucherItems.charge", "stand", "customer"})
    List<Voucher> findAllByCustomer_IdAndStateNot(Long customerId, PaymentState state);

    @EntityGraph(attributePaths = {"voucherItems", "voucherItems.charge", "stand", "customer"})
    List<Voucher> findAllByCustomer_IdAndState(Long customerId, PaymentState state);

    @EntityGraph(attributePaths = {"voucherItems", "voucherItems.charge", "stand", "customer"})
    List<Voucher> findAllByCustomer_Id(Long customerId);

    @EntityGraph(attributePaths = {"voucherItems", "voucherItems.charge", "stand", "customer"})
    List<Voucher> findAllByStand_Id(Long standId);

    @EntityGraph(attributePaths = {"voucherItems", "voucherItems.charge", "stand", "customer"})
    List<Voucher> findAllByIssuer_IdAndState(Long issuerId, PaymentState state);


    long countAllByIssuerIdAndIssueDateBetween(Long issuerId, LocalDate issueDateAfter, LocalDate issueDateBefore);

    long countAllByIssuer_IdAndIssueDate(Long issuerId, LocalDate issueDate);

    @EntityGraph(attributePaths = {"voucherItems", "voucherItems.charge", "stand", "customer"})
    List<Voucher> findAllByStand_IdAndIssuer_IdAndIssueDate(Long standId, Long issuerId, LocalDate issueDate);

    Voucher findTopBySerialNumberStartingWithOrderBySerialNumberDesc(String prefix);

    long countAllByCustomer_IdAndStateNot(Long customerId, PaymentState state);

    @EntityGraph(attributePaths = {"voucherItems", "voucherItems.charge", "stand", "customer"})
    List<Voucher> findAllByIssuer_Id(Long issuerId);
}
