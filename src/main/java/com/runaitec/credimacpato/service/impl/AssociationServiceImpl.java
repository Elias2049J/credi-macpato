package com.runaitec.credimacpato.service.impl;

import com.runaitec.credimacpato.dto.user.UserResponseDTO;
import com.runaitec.credimacpato.entity.user.Association;
import com.runaitec.credimacpato.mapper.UserMapper;
import com.runaitec.credimacpato.repository.UserRepository;
import com.runaitec.credimacpato.service.AssociationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class AssociationServiceImpl implements AssociationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponseDTO> listPartners(Long associationId) {
        Association association = (Association) userRepository.findById(associationId).orElseThrow();
        return association.getPartners()
                .stream()
                .map(userMapper::toResponseDtoDispatch)
                .toList();
    }
}
