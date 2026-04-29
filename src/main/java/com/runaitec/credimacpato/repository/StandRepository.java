package com.runaitec.credimacpato.repository;

import com.runaitec.credimacpato.entity.Stand;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StandRepository extends JpaRepository<Stand, Long> {

    List<Stand> findAllByOwner_Id(Long partnerId);

    Stand findTopByOwner_IdOrderByNumberDesc(Long partnerId);

    @EntityGraph(attributePaths = "owner")
    Optional<Stand> findWithOwnerById(Long id);
}
