package com.runaitec.credimacpato.service;

import com.runaitec.credimacpato.dto.user.UserResponseDTO;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface AssociationService{
    List<UserResponseDTO> listVendors(Long associationId);

    List<UserResponseDTO> listCustomers(Long associationId);

    List<UserResponseDTO> listMembers(Long associationId);
}
