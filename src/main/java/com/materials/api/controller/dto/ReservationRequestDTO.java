package com.materials.api.controller.dto;

import com.materials.api.enums.ReservationStatusEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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

  @NotNull(message = "Status cannot be null")
  private ReservationStatusEnum status;

  @NotNull(message = "User ID cannot be null")
  private Long userId;

  @NotNull(message = "Item ID cannot be null")
  private Long itemId;
}
