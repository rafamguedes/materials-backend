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
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private String code;
  private String status;
  private LocalDateTime createdAt;
  private String userRegistry;
  private Long itemId;
  private String itemType;
}
