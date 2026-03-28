package com.runaitec.credimacpato.repository;

import com.runaitec.credimacpato.entity.Profit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfitRepository extends JpaRepository<Profit, Long> {
}

