package com.runaitec.credimacpato.service.impl;

import com.runaitec.credimacpato.dto.auth.LoginRequestDTO;
import com.runaitec.credimacpato.dto.auth.LoginResponseDTO;
import com.runaitec.credimacpato.dto.user.UserResponseDTO;
import com.runaitec.credimacpato.entity.UserState;
import com.runaitec.credimacpato.entity.user.User;
import com.runaitec.credimacpato.mapper.UserMapper;
import com.runaitec.credimacpato.service.AuthService;
import com.runaitec.credimacpato.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtTokenService jwtTokenService;

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException("Bad Credentials");
        }

        if (userDetails instanceof User u && u.getState() != UserState.ENABLED) {
            throw new DisabledException("User disabled");
        }

        String token = jwtTokenService.generateToken(userDetails);

        UserResponseDTO userDto = null;
        if (userDetails instanceof User u) {
            userDto = userMapper.toResponseDtoDispatch(u);
        }

        return new LoginResponseDTO(userDto, token);
    }
}
