package com.materials.api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  private final Algorithm algorithm;

  @Value("${spring.security.token.secret}")
  private String secret;

  @Value("${spring.security.token.expiration}")
  private Long accessTokenExpiration;

  @Value("${spring.security.refresh.token.expiration}")
  private Long refreshTokenExpiration;

  public TokenService(@Value("${spring.security.token.secret}") String secret) {
    this.algorithm = Algorithm.HMAC256(secret);
  }

  public String generateToken(String email) {
    return JWT.create().withSubject(email).withExpiresAt(generateExpiration()).sign(algorithm);
  }

  public String generateRefreshToken(String email) {
    return JWT.create()
        .withSubject(email)
        .withExpiresAt(Instant.now().plus(refreshTokenExpiration, ChronoUnit.DAYS))
        .sign(algorithm);
  }

  private Instant generateExpiration() {
    return Instant.now().plus(1, ChronoUnit.HOURS);
  }

  public String validateToken(String token) {
    return JWT.require(algorithm).build().verify(token).getSubject();
  }
}
