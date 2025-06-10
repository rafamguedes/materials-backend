package com.materials.api.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.materials.api.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO implements Serializable {
  private Long id;
  private String name;
  private String email;
  private String registry;
  private Boolean active;
  private Boolean isDeleted;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime deletedAt;
  private Long addressId;
}
