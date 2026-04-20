package com.runaitec.credimacpato.repository;

import com.runaitec.credimacpato.entity.Charge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChargeRepository extends JpaRepository<Charge, Long> {
    List<Charge> findAllByStand_Id(Long standId);
}
