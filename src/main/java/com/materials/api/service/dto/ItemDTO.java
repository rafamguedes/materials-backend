package com.materials.api.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDTO {
  private Long id;
  private String name;
  private String description;
  private String type;
  private String status;
  private String serialNumber;
  private String createdAt;
  private String updatedAt;
}
