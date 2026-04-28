package com.runaitec.credimacpato.service;

import com.runaitec.credimacpato.dto.user.UserRequestDTO;
import com.runaitec.credimacpato.dto.user.UserResponseDTO;

import com.runaitec.credimacpato.entity.user.User;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface UserService{
    UserResponseDTO register(UserRequestDTO request);
    void delete(Long id);

    List<UserResponseDTO> findAll();

    User getEntityById(Long aLong);

    UserResponseDTO findById(Long aLong);

    UserResponseDTO disable(Long id);
    UserResponseDTO enable(Long id);
    UserResponseDTO block(Long id);

    UserResponseDTO update(Long aLong, UserRequestDTO request);

    List<UserResponseDTO> searchByName(String q);
}
