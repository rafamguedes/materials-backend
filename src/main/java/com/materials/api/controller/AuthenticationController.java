package com.materials.api.controller;

import com.materials.api.controller.dto.AuthDTO;
import com.materials.api.controller.dto.RefreshTokenDTO;
import com.materials.api.controller.dto.TokenDTO;
import com.materials.api.entity.User;
import com.materials.api.service.TokenService;
import com.materials.api.service.exceptions.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication", description = "Endpoints for user authentication and token management")
public class AuthenticationController {

  private static final String INVALID_USERNAME_OR_PASSWORD = "Nome de usuário ou senha inválidos.";
  private static final String SUCCESSFULLY_LOGGED_OUT = "Logout realizado com sucesso.";
  private static final String INVALID_REFRESH_TOKEN = "Refresh token inválido ou expirado";

  private final AuthenticationManager authenticationManager;
  private final TokenService tokenService;

  @PostMapping("/login")
  @Operation(description = "Logs in a user and returns an access token and refresh token.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Login successful, returns access and refresh tokens"),
    @ApiResponse(responseCode = "400", description = "Invalid username or password",
      content = @Content(schema = @Schema(type = "string", example = INVALID_USERNAME_OR_PASSWORD))) })
  public ResponseEntity<TokenDTO> login(@RequestBody AuthDTO req) {
    try {
      var pass = new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword());
      var auth = authenticationManager.authenticate(pass);
      var userDetails = (User) auth.getPrincipal();

      var accessToken = tokenService.generateToken(auth.getName());
      var refreshToken = tokenService.generateRefreshToken(auth.getName());

      var id = userDetails.getId();
      var name = userDetails.getName();
      var email = userDetails.getEmail();
      var role = userDetails.getRole();

      return ResponseEntity.ok(new TokenDTO(accessToken, refreshToken, id, name, email, role));
    } catch (BadCredentialsException e) {
      throw new BadRequestException(INVALID_USERNAME_OR_PASSWORD);
    }
  }

  @PostMapping("/refresh")
  @Operation(description = "Refreshes the access token using a valid refresh token.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Refresh successful, returns new access and refresh tokens"),
    @ApiResponse(responseCode = "400", description = "Invalid or expired refresh token",
      content = @Content(schema = @Schema(type = "string", example = "Refresh token inválido ou expirado"))) })
  public ResponseEntity<TokenDTO> refresh(@RequestBody RefreshTokenDTO req) {
    try {
      var email = tokenService.validateToken(req.getRefreshToken());
      var newAccessToken = tokenService.generateToken(email);
      var newRefreshToken = tokenService.generateRefreshToken(email);

      var userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      var id = userDetails.getId();
      var name = userDetails.getName();
      var role = userDetails.getRole();

      return ResponseEntity.ok(new TokenDTO(newAccessToken, newRefreshToken, id, name, email, role));
    } catch (Exception e) {
      throw new BadRequestException(INVALID_REFRESH_TOKEN);
    }
  }

  @PostMapping("/logout")
  @Operation(description = "Logs out the user by clearing the security context.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Logout successful"),
    @ApiResponse(responseCode = "400", description = "Logout failed", content = @Content(schema = @Schema())) })
  public ResponseEntity<String> logout() {
    SecurityContextHolder.clearContext();
    return ResponseEntity.ok().body(SUCCESSFULLY_LOGGED_OUT);
  }
}
