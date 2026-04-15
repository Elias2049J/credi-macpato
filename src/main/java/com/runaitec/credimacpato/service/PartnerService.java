package com.runaitec.credimacpato.service;

import com.runaitec.credimacpato.dto.user.UserRequestDTO;
import com.runaitec.credimacpato.dto.user.UserResponseDTO;

import java.util.List;

public interface PartnerService {
    UserResponseDTO create(UserRequestDTO request);
    UserResponseDTO update(Long partnerId, UserRequestDTO request);
    UserResponseDTO findById(Long partnerId);
    void delete(Long partnerId);

    List<UserResponseDTO> listByAssociation(Long associationId);

    UserResponseDTO deactivate(Long partnerId);
}
