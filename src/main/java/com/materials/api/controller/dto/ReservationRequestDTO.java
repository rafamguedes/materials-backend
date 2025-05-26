package com.materials.api.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestDTO {
  @NotNull(message = "Date and time cannot be null")
  private LocalDateTime dateTime;

  @NotNull(message = "User registry cannot be null")
  private String userRegistry;

  @NotNull(message = "Item ID cannot be null")
  private Long itemId;
}
