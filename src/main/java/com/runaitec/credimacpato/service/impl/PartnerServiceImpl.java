package com.runaitec.credimacpato.service.impl;

import com.runaitec.credimacpato.dto.user.UserResponseDTO;
import com.runaitec.credimacpato.service.PartnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class PartnerServiceImpl implements PartnerService {
    @Override
    public List<UserResponseDTO> listByAssociation(Long associationId) {
        return List.of();
    }
}
