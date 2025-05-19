package com.materials.api.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationDTO implements Serializable {
  private Long id;
  private LocalDateTime dateTime;
  private String status;
  private String code;
  private Long userId;
  private Long itemId;
}
