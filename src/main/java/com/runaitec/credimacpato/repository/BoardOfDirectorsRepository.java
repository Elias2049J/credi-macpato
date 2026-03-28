package com.runaitec.credimacpato.repository;

import com.runaitec.credimacpato.entity.BoardOfDirectors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardOfDirectorsRepository extends JpaRepository<BoardOfDirectors, Long> {
}

