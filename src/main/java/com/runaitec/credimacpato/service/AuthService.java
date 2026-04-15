package com.runaitec.credimacpato.service;

import com.runaitec.credimacpato.dto.auth.LoginRequestDTO;
import com.runaitec.credimacpato.dto.auth.LoginResponseDTO;
import com.runaitec.credimacpato.dto.user.UserRequestDTO;
import com.runaitec.credimacpato.dto.user.UserResponseDTO;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO request);
    UserResponseDTO register(UserRequestDTO request);
}

