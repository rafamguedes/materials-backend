package com.materials.api.controller.dto;

import com.materials.api.enums.ReservationStatusEnum;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationUpdateRequestDTO {
  @NotNull(message = "Date and time cannot be null")
  private LocalDateTime dateTime;

  @NotNull(message = "Status cannot be null")
  private ReservationStatusEnum status;

  @NotNull(message = "Item ID cannot be null")
  private Long itemId;
}
