package com.runaitec.credimacpato.service.impl;

import com.runaitec.credimacpato.dto.user.UserRequestDTO;
import com.runaitec.credimacpato.dto.user.UserResponseDTO;
import com.runaitec.credimacpato.dto.user.customer.CustomerRequestDTO;
import com.runaitec.credimacpato.dto.user.vendor.VendorRequestDTO;
import com.runaitec.credimacpato.entity.UserState;
import com.runaitec.credimacpato.entity.user.Association;
import com.runaitec.credimacpato.entity.user.Customer;
import com.runaitec.credimacpato.entity.user.Vendor;
import com.runaitec.credimacpato.entity.user.User;
import com.runaitec.credimacpato.mapper.UserMapper;
import com.runaitec.credimacpato.repository.AssociationRepository;
import com.runaitec.credimacpato.repository.BusinessCustomerRepository;
import com.runaitec.credimacpato.repository.PersonCustomerRepository;
import com.runaitec.credimacpato.repository.UserRepository;
import com.runaitec.credimacpato.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AssociationRepository associationRepository;
    private final PersonCustomerRepository personCustomerRepository;
    private final BusinessCustomerRepository businessCustomerRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO register(UserRequestDTO request) {
        User user = userMapper.toEntityDispatch(request);
        if(userRepository.existsByUsername(request.getUsername()))
            throw new IllegalArgumentException("Username already taken");
        if (user instanceof Vendor pa && request instanceof VendorRequestDTO paReq) {
            pa.setAssociation((Association) userRepository.findById(paReq.getAssociationId()).orElseThrow());
        }
        if (user instanceof Customer cu && request instanceof CustomerRequestDTO cuReq) {
            cu.setAssociation((Association) userRepository.findById(cuReq.getAssociationId()).orElseThrow());
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userMapper.toResponseDtoDispatch(userRepository.save(user));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDtoDispatch)
                .toList();
    }

    @Override
    public User getEntityById(Long aLong) {
        return userRepository.findById(aLong).orElseThrow();
    }

    @Override
    public UserResponseDTO findById(Long aLong) {
        return userMapper.toResponseDtoDispatch(getEntityById(aLong));
    }

    @Override
    public UserResponseDTO disable(Long id) {
        User user = getEntityById(id);
        user.setState(UserState.DISABLED);
        return userMapper.toResponseDtoDispatch(
                userRepository.save(user)
        );
    }

    @Override
    public UserResponseDTO enable(Long id) {
        User user = getEntityById(id);
        user.setState(UserState.ENABLED);
        return userMapper.toResponseDtoDispatch(
                userRepository.save(user)
        );
    }

    @Override
    public UserResponseDTO block(Long id) {
        User user = getEntityById(id);
        user.setState(UserState.BLOCKED);
        return userMapper.toResponseDtoDispatch(
                userRepository.save(user)
        );
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserResponseDTO update(Long id, UserRequestDTO request) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        userMapper.updateFromDtoDispatch(request, existing);
        if (request instanceof CustomerRequestDTO c && existing instanceof Customer e) {
            e.setAssociation(
                    associationRepository.getReferenceById(c.getAssociationId())
            );
        } else if (request instanceof VendorRequestDTO v && existing instanceof Vendor e) {
            e.setAssociation(
                    associationRepository.getReferenceById(v.getAssociationId())
            );
        }
        existing.setPassword(passwordEncoder.encode(request.getPassword()));

        return userMapper.toResponseDtoDispatch(
                userRepository.save(existing)
        );
    }

    @Override
    public List<UserResponseDTO> searchByName(String q) {
        return Stream.concat(
                personCustomerRepository.findByNameContainingIgnoreCase(q).stream(),
                businessCustomerRepository.findByRegistrationNameContainingIgnoreCase(q).stream()
        ).map(userMapper::toResponseDtoDispatch)
                .toList();
    }
}
