package com.runaitec.credimacpato.repository;

import com.runaitec.credimacpato.entity.user.Association;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssociationRepository extends JpaRepository<Association, Long> {

    @EntityGraph(attributePaths = {"vendors", "customers"})
    Optional<Association> findWithMembersById(Long id);
}
