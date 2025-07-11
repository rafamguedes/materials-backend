package com.materials.api.utils;

import com.materials.api.entity.Item;
import com.materials.api.entity.Reservation;
import com.materials.api.entity.User;

import java.time.format.DateTimeFormatter;

public class EmailTemplateUtils {

  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

  public static String getReservationConfirmationEmail(
      User user, Item item, Reservation reservation) {
    return String.format(
        """
                Olá %s,

                Sua reserva foi confirmada com sucesso!

                Detalhes da reserva:
                Código: %s
                Item: %s
                Data/Hora: %s
                Status: %s

                Atenciosamente,
                Equipe de Reservas
                """,
        user.getName(),
        reservation.getCode(),
        item.getName(),
        reservation.getDateTime().format(DATE_TIME_FORMATTER),
        reservation.getStatus().name());
  }

  public static String getReservationCancellationEmail(
      User user, Item item, Reservation reservation) {
    return String.format(
        """
                Olá %s,

                Sua reserva foi cancelada.

                Detalhes da reserva:
                Código: %s
                Item: %s
                Data/Hora: %s
                Status: %s

                Atenciosamente,
                Equipe de Reservas
                """,
        user.getName(),
        reservation.getCode(),
        item.getName(),
        reservation.getDateTime().format(DATE_TIME_FORMATTER),
        reservation.getStatus().name());
  }

  public static String getReservationStartedEmail(User user, Item item, Reservation reservation) {
    return String.format(
        """
                    Olá %s,

                    Sua reserva foi iniciada com sucesso!

                    Detalhes da reserva:
                    Código: %s
                    Item: %s
                    Data/Hora: %s
                    Status: %s

                    Atenciosamente,
                    Equipe de Reservas
                    """,
        user.getName(),
        reservation.getCode(),
        item.getName(),
        reservation.getDateTime().format(DATE_TIME_FORMATTER),
        reservation.getStatus().name());
  }

  public static String getReservationFinishedEmail(User user, Item item, Reservation reservation) {
    return String.format(
        """
                    Olá %s,

                    Sua reserva foi finalizada com sucesso!

                    Detalhes da reserva:
                    Código: %s
                    Item: %s
                    Data/Hora: %s
                    Status: %s

                    Atenciosamente,
                    Equipe de Reservas
                    """,
        user.getName(),
        reservation.getCode(),
        item.getName(),
        reservation.getDateTime().format(DATE_TIME_FORMATTER),
        reservation.getStatus().name());
  }
}
