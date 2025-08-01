package com.materials.api.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRequestDTO {
  @NotEmpty(message = "O nome não pode estar vazio.")
  private String name;

  @NotEmpty(message = "O e-mail não pode estar vazio.")
  @Email(message = "O e-mail deve ser válido.")
  private String email;

  @NotEmpty(message = "A senha não pode estar vazia.")
  @Size(min = 8, max = 20, message = "A senha deve ter entre 8 e 20 caracteres.")
  private String password;

  private String registry;

  private Boolean active;

  @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "CEP deve estar no formato XXXXX-XXX ou XXXXXXXX")
  private String postalCode;
}
