package com.materials.api.service;

import static com.materials.api.service.exceptions.messages.ItemMessages.ITEM_NOT_FOUND;
import static com.materials.api.service.exceptions.messages.ReservationMessages.*;

import com.materials.api.enums.ItemStatusEnum;
import com.materials.api.enums.ReservationStatusEnum;
import com.materials.api.repository.ItemRepository;
import com.materials.api.repository.ReservationRepository;
import com.materials.api.service.exceptions.BadRequestException;
import com.materials.api.service.exceptions.NotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationStatusService {

  private final ReservationRepository reservationRepository;
  private final NotifierService notifierService;
  private final ItemRepository itemRepository;

  public void cancel(String code) {
    var reservation =
        reservationRepository
            .findByCode(code.trim())
            .orElseThrow(() -> new NotFoundException(RESERVATION_NOT_FOUND));

    if (!ReservationStatusEnum.PENDING.equals(reservation.getStatus())) {
      throw new BadRequestException(RESERVATION_CANCEL_VALIDATE_STATUS);
    }

    var item =
        itemRepository
            .findById(reservation.getItem().getId())
            .orElseThrow(
                () -> new NotFoundException(ITEM_NOT_FOUND + reservation.getItem().getId()));

    reservation.setStatus(ReservationStatusEnum.CANCELLED);
    item.setStatus(ItemStatusEnum.AVAILABLE);
    notifierService.sendCancellationReservationNotification(reservation.getUser(), item, reservation);
    reservationRepository.save(reservation);
  }

  public void start(String code) {
    var reservation =
        reservationRepository
            .findByCode(code)
            .orElseThrow(() -> new NotFoundException(RESERVATION_NOT_FOUND));

    if (!ReservationStatusEnum.PENDING.equals(reservation.getStatus())) {
      throw new BadRequestException(RESERVATION_START_VALIDATE_STATUS);
    }

    var item =
        itemRepository
            .findById(reservation.getItem().getId())
            .orElseThrow(
                () -> new NotFoundException(ITEM_NOT_FOUND + reservation.getItem().getId()));

    reservation.setStatus(ReservationStatusEnum.IN_PROGRESS);
    reservation.setStartTime(LocalDateTime.now());
    item.setStatus(ItemStatusEnum.RESERVED);
    notifierService.sendStartReservationNotification(reservation.getUser(), item, reservation);
    reservationRepository.save(reservation);
  }

  public void finish(String code) {
    var reservation =
        reservationRepository
            .findByCode(code)
            .orElseThrow(() -> new NotFoundException(RESERVATION_NOT_FOUND));

    if (!ReservationStatusEnum.IN_PROGRESS.equals(reservation.getStatus())) {
      throw new BadRequestException(RESERVATION_COMPLETE_VALIDATE_STATUS);
    }

    var item =
        itemRepository
            .findById(reservation.getItem().getId())
            .orElseThrow(
                () -> new NotFoundException(ITEM_NOT_FOUND + reservation.getItem().getId()));

    reservation.setStatus(ReservationStatusEnum.CONFIRMED);
    reservation.setEndTime(LocalDateTime.now());
    item.setStatus(ItemStatusEnum.AVAILABLE);
    notifierService.sendFinishReservationNotification(reservation.getUser(), item, reservation);
    reservationRepository.save(reservation);
  }
}
