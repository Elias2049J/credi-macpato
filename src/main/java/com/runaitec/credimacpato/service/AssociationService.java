package com.runaitec.credimacpato.service;

import com.runaitec.credimacpato.dto.user.UserRequestDTO;
import com.runaitec.credimacpato.dto.user.UserResponseDTO;

import java.util.List;

public interface AssociationService {
    UserResponseDTO create(UserRequestDTO request);
    UserResponseDTO update(Long associationId, UserRequestDTO request);
    UserResponseDTO findById(Long associationId);
    void delete(Long associationId);

    List<UserResponseDTO> listPartners(Long associationId);

    UserResponseDTO deactivate(Long associationId);
}
