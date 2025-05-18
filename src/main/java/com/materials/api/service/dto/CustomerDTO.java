package com.materials.api.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {
  private Long id;
  private String name;
  private String email;
  private String phone;
}
