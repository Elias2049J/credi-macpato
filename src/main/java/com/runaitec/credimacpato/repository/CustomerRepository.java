package com.runaitec.credimacpato.repository;

import com.runaitec.credimacpato.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findAllByAssociation_Id(Long associationId);
}

