package com.materials.api.security;

import com.materials.api.service.TokenService;
import com.materials.api.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

  private final TokenService tokenService;
  private final UserService userService;

  private static final String AUTHORIZATION = "Authorization";
  private static final String BEARER = "Bearer ";

  @Autowired
  public JwtFilter(TokenService tokenService, UserService userService) {
    this.tokenService = tokenService;
    this.userService = userService;
  }

  @Override
  protected void doFilterInternal(
          HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {
    var token = extractToken(request);
    if (token.isPresent()) {
      var subject = tokenService.validateToken(token.get());
      var userDetails = userService.loadUserByUsername(subject);
      var authentication =
              new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }

  private Optional<String> extractToken(HttpServletRequest request) {
    var authHeader = request.getHeader(AUTHORIZATION);
    if (Objects.isNull(request.getHeader(AUTHORIZATION))) {
      return Optional.empty();
    }

    return Optional.of(authHeader.replace(BEARER, ""));
  }
}
