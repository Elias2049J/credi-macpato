package com.runaitec.credimacpato.service;

import com.runaitec.credimacpato.dto.user.UserRequestDTO;
import com.runaitec.credimacpato.dto.user.UserResponseDTO;

import com.runaitec.credimacpato.entity.user.User;

public interface UserService extends CrudService<UserResponseDTO, UserRequestDTO, Long, User>{
    UserResponseDTO disable(Long id);
    UserResponseDTO enable(Long id);
    UserResponseDTO block(Long id);
}
