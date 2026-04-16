package com.runaitec.credimacpato.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.runaitec.credimacpato.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    @Value("${security.jwt.issuer:credimacpato-api}")
    private String issuer;

    @Value("${security.jwt.expiration-seconds:3600}")
    private long expirationSeconds;

    @Value("${security.jwt.secret}")
    private String secret;

    private Algorithm algorithm;

    @PostConstruct
    void init() {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    @Override
    public String generateToken(UserDetails user) {
        Instant now = Instant.now();

        List<String> authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return JWT.create()
                .withIssuer(issuer)
                .withSubject(user.getUsername())
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plusSeconds(expirationSeconds)))
                .withClaim("authorities", authorities)
                .sign(algorithm);
    }

    @Override
    public DecodedJWT decodeAndVerify(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build();
            return verifier.verify(token);
        } catch (JWTVerificationException ex) {
            throw new IllegalArgumentException("Token inválido");
        }
    }
}
