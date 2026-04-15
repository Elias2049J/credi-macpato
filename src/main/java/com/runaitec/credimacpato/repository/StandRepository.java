package com.runaitec.credimacpato.repository;

import com.runaitec.credimacpato.entity.Stand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandRepository extends JpaRepository<Stand, Long> {
}

