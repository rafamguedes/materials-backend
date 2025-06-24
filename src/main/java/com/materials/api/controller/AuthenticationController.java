package com.materials.api.controller;

import com.materials.api.controller.dto.AuthDTO;
import com.materials.api.controller.dto.TokenDTO;
import com.materials.api.entity.User;
import com.materials.api.service.TokenService;
import com.materials.api.service.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

  private static final String INVALID_USERNAME_OR_PASSWORD = "Invalid username or password. Please try again.";
  private static final String SUCCESSFULLY_LOGGED_OUT = "Successfully logged out of the system.";

  private final AuthenticationManager authenticationManager;
  private final TokenService tokenService;

  @PostMapping("/login")
  public ResponseEntity<TokenDTO> login(@RequestBody AuthDTO req) {
    try {
      var pass = new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword());
      var auth = authenticationManager.authenticate(pass);
      var userDetails = (User) auth.getPrincipal();
      var token = tokenService.generateToken(auth.getName());

      var id = userDetails.getId();
      var name = userDetails.getName();
      var email = userDetails.getEmail();
      var role = userDetails.getRole();

      return ResponseEntity.ok(new TokenDTO(token, id, name, email, role));
    } catch (BadCredentialsException ex) {
      throw new BadRequestException(INVALID_USERNAME_OR_PASSWORD);
    }
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout() {
    SecurityContextHolder.clearContext();
    return ResponseEntity.ok().body(SUCCESSFULLY_LOGGED_OUT);
  }
}
