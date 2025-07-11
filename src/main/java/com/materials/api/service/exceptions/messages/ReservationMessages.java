package com.materials.api.service.exceptions.messages;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReservationMessages {

  // Reservation Service Messages
  public static final String RESERVATION_NOT_FOUND =
       "Reservation not found, please check the reservation.";
  public static final String RESERVATION_CANCEL_VALIDATE_STATUS =
      "A reserva só pode ser cancelada se estiver com o status Pendente.";

  // Reservation Status Service Messages
  public static final String RESERVATION_START_VALIDATE_STATUS =
      "Reservation can only be started if it is in PENDING status.";
  public static final String RESERVATION_COMPLETE_VALIDATE_STATUS =
      "Reservation can only be completed if it is in IN_PROGRESS status.";
}
