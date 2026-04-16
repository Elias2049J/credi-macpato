package com.runaitec.credimacpato.service;

import com.runaitec.credimacpato.dto.user.UserResponseDTO;

import java.util.List;

public interface AssociationService{
    List<UserResponseDTO> listPartners(Long associationId);
}
