package com.materials.api.enums;

import lombok.Getter;

@Getter
public enum ItemStatusEnum {
  AVAILABLE,
  RESERVED,
  IN_MAINTENANCE,
  OUT_OF_SERVICE,
  LOST
}
