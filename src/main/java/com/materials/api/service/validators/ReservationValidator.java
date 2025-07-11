package com.materials.api.service.validators;

import com.materials.api.controller.dto.ReservationRequestDTO;
import com.materials.api.repository.ReservationRepository;
import com.materials.api.service.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationValidator {

  private static final String RESERVATION_ALREADY_EXISTS =
      "Reservation already exists for the date and time: ";

  private final ReservationRepository reservationRepository;

  public void validateDateTime(ReservationRequestDTO requestDTO) {
    reservationRepository
        .findByDateTime(requestDTO.getDateTime())
        .ifPresent(
            reservation -> {
              throw new BadRequestException(RESERVATION_ALREADY_EXISTS + requestDTO.getDateTime());
            });
  }
}
