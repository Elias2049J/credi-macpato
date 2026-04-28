package com.runaitec.credimacpato.repository;

import com.runaitec.credimacpato.entity.user.BusinessCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessCustomerRepository extends JpaRepository<BusinessCustomer, Long> {
    List<BusinessCustomer> findByRegistrationNameContainingIgnoreCase(String name);
}
