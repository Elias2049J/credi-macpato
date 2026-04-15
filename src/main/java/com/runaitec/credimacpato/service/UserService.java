package com.runaitec.credimacpato.service;

import com.runaitec.credimacpato.dto.user.UserRequestDTO;
import com.runaitec.credimacpato.dto.user.UserResponseDTO;

import java.util.List;

public interface UserService {
    List<UserResponseDTO> findAll();
    UserResponseDTO findById(Long id);

    UserResponseDTO create(UserRequestDTO request);
    UserResponseDTO update(Long id, UserRequestDTO request);

    void delete(Long id);

    UserResponseDTO deactivate(Long id);
    UserResponseDTO activate(Long id);
    UserResponseDTO block(Long id);
}
