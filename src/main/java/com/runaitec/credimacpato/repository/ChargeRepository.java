package com.runaitec.credimacpato.repository;

import com.runaitec.credimacpato.entity.Charge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChargeRepository extends JpaRepository<Charge, Long> {
    Optional<Charge> findByName(String name);
}

