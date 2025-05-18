package com.materials.api.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.materials.api.entity.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO {
  private Long id;
  private String name;
  private String email;
  private String phone;
  private Address address;
}
