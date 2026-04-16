package com.runaitec.credimacpato.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenService {
    String generateToken(UserDetails user);
    DecodedJWT decodeAndVerify(String token);
}
