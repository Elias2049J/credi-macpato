package com.runaitec.credimacpato.repository;

import com.runaitec.credimacpato.entity.VoucherItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherItemRepository extends JpaRepository<VoucherItem, Integer> {
}

