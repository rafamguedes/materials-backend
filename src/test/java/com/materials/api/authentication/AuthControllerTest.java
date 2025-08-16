package com.materials.api.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.materials.api.controller.dto.AuthDTO;
import com.materials.api.entity.User;
import com.materials.api.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerTest {

  private static final String ROLE = "ROLE_USER";
  private static final String EMAIL = "email-test@email.com";
  private static final String PASSWORD = "password-test";
  private static final String URL = "/api/v1/authentication/login";
  private static final String TOKEN = "mocked-token";

  @Autowired private MockMvc mockMvc;

  @MockitoBean private AuthenticationManager authenticationManager;

  @MockitoBean private TokenService tokenService;

  @BeforeEach
  public void setUp() {
    var authority = new SimpleGrantedAuthority(ROLE);

    var user = new User();
    user.setEmail(EMAIL);
    user.setPassword(PASSWORD);

    var authentication =
        new UsernamePasswordAuthenticationToken(user, null, Collections.singleton(authority));

    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(authentication);

    when(tokenService.generateToken(EMAIL)).thenReturn(TOKEN);
  }

  @Test
  public void testLoginSuccess() throws Exception {
    var authDto = new AuthDTO(EMAIL, PASSWORD);

    var result =
        mockMvc.perform(
            MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(authDto)));

    result.andExpect(MockMvcResultMatchers.status().isOk());
    result.andExpect(MockMvcResultMatchers.jsonPath("$.token").value(TOKEN));
  }

  @Test
  public void testLoginInvalidCredentials() throws Exception {
    var authDto = new AuthDTO(EMAIL, PASSWORD);

    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenThrow(new BadCredentialsException("Credenciais inválidas"));

    var result =
        mockMvc.perform(
            MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(authDto)));

    result.andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}
