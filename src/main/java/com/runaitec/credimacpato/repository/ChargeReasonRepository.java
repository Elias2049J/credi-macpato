package com.runaitec.credimacpato.repository;

import com.runaitec.credimacpato.entity.ChargeReason;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChargeReasonRepository extends JpaRepository<ChargeReason, Long> {
    Optional<ChargeReason> findByName(String name);
}

