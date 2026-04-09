package com.runaitec.credimacpato.usecase;

import com.runaitec.credimacpato.dto.UserDTO;
import com.runaitec.credimacpato.entity.User;

public interface UserUseCase {
    UserDTO login(UserDTO userDTO);
    UserDTO register(UserDTO userDto);
}