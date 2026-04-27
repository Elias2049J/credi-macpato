package com.runaitec.credimacpato.service.impl;

import com.runaitec.credimacpato.dto.user.UserResponseDTO;
import com.runaitec.credimacpato.entity.user.User;
import com.runaitec.credimacpato.mapper.UserMapper;
import com.runaitec.credimacpato.repository.AssociationRepository;
import com.runaitec.credimacpato.repository.CustomerRepository;
import com.runaitec.credimacpato.repository.VendorRepository;
import com.runaitec.credimacpato.service.AssociationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class AssociationServiceImpl implements AssociationService {

    private final VendorRepository vendorRepository;
    private final CustomerRepository customerRepository;
    private final AssociationRepository associationRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponseDTO> listVendors(Long associationId) {
        return vendorRepository.findAllByAssociation_Id(associationId)
                .stream()
                .map(userMapper::toResponseDtoDispatch)
                .toList();
    }

    @Override
    public List<UserResponseDTO> listCustomers(Long associationId) {
        return customerRepository.findAllByAssociation_Id(associationId)
                .stream()
                .map(userMapper::toResponseDtoDispatch)
                .toList();
    }

    @Override
    public List<UserResponseDTO> listMembers(Long associationId) {
        var association = associationRepository.findWithMembersById(associationId)
                .orElseThrow(() -> new IllegalArgumentException("Association not found: " + associationId));

        return Stream.concat(
                        association.getVendors() == null ? Stream.empty() : association.getVendors().stream(),
                        association.getCustomers() == null ? Stream.empty() : association.getCustomers().stream()
                )
                .map(userMapper::toResponseDtoDispatch)
                .toList();
    }

    @Override
    public List<UserResponseDTO> findAll() {
        return associationRepository.findAll()
                .stream()
                .map(userMapper::toResponseDtoDispatch)
                .toList();
    }
}
