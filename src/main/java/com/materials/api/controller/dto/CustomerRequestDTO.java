package com.materials.api.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class CustomerRequestDTO {
  @NotEmpty private String name;
  @NotEmpty @Email private String email;
  @NotEmpty private String phone;
}
