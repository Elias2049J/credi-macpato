package com.runaitec.credimacpato.repository;

import com.runaitec.credimacpato.entity.VoucherItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherItemRepository extends JpaRepository<VoucherItem, Long> {
    List<VoucherItem> findAllByPayment_Id(Long paymentId);
}

