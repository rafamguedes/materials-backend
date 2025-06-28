package com.materials.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.materials.api.security.Role;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TokenDTO(String token, String refreshToken, Long id, String name, String email, Role role) {}
