package com.materials.api.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDTO {
  @NotEmpty(message = "O e-mail não pode estar vazio.")
  @Email(message = "O e-mail deve ser válido.")
  @Size(max = 50, message = "O e-mail deve ter no máximo 50 caracteres.")
  private String email;

  @NotEmpty(message = "A senha não pode estar vazia.")
  @Size(min = 8, max = 20, message = "A senha deve ter entre 8 e 20 caracteres.")
  private String password;
}