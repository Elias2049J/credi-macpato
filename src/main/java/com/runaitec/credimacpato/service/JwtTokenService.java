package com.runaitec.credimacpato.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public interface JwtTokenService {
    String generateToken(UserDetails user);

    DecodedJWT verify(String token);

    String extractSubject(DecodedJWT jwt);

    Collection<String> extractAuthorities(DecodedJWT jwt);

    AbstractAuthenticationToken buildAuthentication(String subject, Collection<String> authorities);
}
