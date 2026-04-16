package com.runaitec.credimacpato.service;

import com.runaitec.credimacpato.dto.user.UserResponseDTO;

import java.util.List;

public interface PartnerService {
    List<UserResponseDTO> listByAssociation(Long associationId);
}
