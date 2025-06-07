package com.materials.api.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ItemDTO implements Serializable {
  private Long id;
  private String name;
  private String description;
  private String itemType;
  private String status;
  private String serialNumber;
}
