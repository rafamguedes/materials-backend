package com.materials.api.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserRequestDTO {
  @NotEmpty
  private String name;

  @NotEmpty
  @Email
  private String email;

  @NotEmpty
  private String registry;

  @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "CEP deve estar no formato XXXXX-XXX ou XXXXXXXX")
  private String postalCode;
}
