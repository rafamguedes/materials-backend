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

  public TokenService(@Value("${spring.security.token.secret}") String secret) {
    this.algorithm = Algorithm.HMAC256(secret);
  }

  public String generateToken(String email) {
    return JWT.create().withSubject(email).withExpiresAt(generateExpiration()).sign(algorithm);
  }

  private Instant generateExpiration() {
    return Instant.now().plus(1, ChronoUnit.HOURS);
  }

  public String validateToken(String token) {
    return JWT.require(algorithm).build().verify(token).getSubject();
  }
}
