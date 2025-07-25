package com.materials.api.service;

import static com.materials.api.service.exceptions.messages.ItemMessages.ITEM_NOT_FOUND;
import static com.materials.api.service.exceptions.messages.UserMessages.USER_NOT_FOUND;
import static com.materials.api.service.exceptions.messages.ReservationMessages.*;
import static com.materials.api.utils.GenerateCode.generateCode;
import static com.materials.api.utils.TokenUtils.getNextToken;

import com.materials.api.controller.dto.ReservationFilterDTO;
import com.materials.api.controller.dto.ReservationRequestDTO;
import com.materials.api.controller.dto.ReservationUpdateRequestDTO;
import com.materials.api.entity.Reservation;
import com.materials.api.enums.ReservationStatusEnum;
import com.materials.api.pagination.PaginationDTO;
import com.materials.api.repository.ItemRepository;
import com.materials.api.repository.ReservationRepository;
import com.materials.api.repository.UserRepository;
import com.materials.api.service.dto.ReservationDTO;
import com.materials.api.service.exceptions.NotFoundException;
import com.materials.api.service.validators.ReservationValidator;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

  private final ReservationRepository reservationRepository;
  private final ReservationValidator reservationValidator;
  private final NotifierService notifierService;
  private final UserRepository userRepository;
  private final ItemRepository itemRepository;
  private final ModelMapper modelMapper;

  public ReservationDTO create(ReservationRequestDTO requestDTO) {
    reservationValidator.validateDateTime(requestDTO);

    var user =
        userRepository
            .findByRegistry(requestDTO.getUserRegistry())
            .orElseThrow(
                () -> new NotFoundException(USER_NOT_FOUND + requestDTO.getUserRegistry()));

    var item =
        itemRepository
            .findById(requestDTO.getItemId())
            .orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND + requestDTO.getItemId()));

    var reservation =
        Reservation.builder()
            .dateTime(requestDTO.getDateTime())
            .status(ReservationStatusEnum.PENDING)
            .code(generateCode())
            .user(user)
            .item(item)
            .build();

    var savedReservation = reservationRepository.save(reservation);
    notifierService.sendConfirmationReservationNotification(user, item, savedReservation);
    return modelMapper.map(savedReservation, ReservationDTO.class);
  }

  public void update(Long id, ReservationUpdateRequestDTO requestDTO) {
    var reservation =
        reservationRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(RESERVATION_NOT_FOUND));

    Optional.ofNullable(requestDTO.getItemId())
        .ifPresent(
            itemId -> {
              var item =
                  itemRepository
                      .findById(itemId)
                      .orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND + itemId));
              reservation.setItem(item);
            });

    Optional.ofNullable(requestDTO.getDateTime()).ifPresent(reservation::setDateTime);

    reservationRepository.saveAndFlush(reservation);
  }

  public void hardDelete(Long id) {
    if (!reservationRepository.existsById(id)) {
      throw new NotFoundException(RESERVATION_NOT_FOUND);
    }
    reservationRepository.deleteById(id);
  }

  public void softDelete(Long id) {
    var reservation =
        reservationRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(RESERVATION_NOT_FOUND));

    reservation.setDeleted(true);
    reservation.setDeletedAt(LocalDateTime.now());
    reservationRepository.save(reservation);
  }

  public void recoverDelete(Long id) {
    var reservation =
        reservationRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(RESERVATION_NOT_FOUND));

    reservation.setDeleted(false);
    reservation.setDeletedAt(null);
    reservationRepository.save(reservation);
  }

  public ReservationDTO getById(Long id) {
    var reservation =
        reservationRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(RESERVATION_NOT_FOUND));

    return modelMapper.map(reservation, ReservationDTO.class);
  }

  public ReservationDTO getByCode(String code) {
    var reservation =
        reservationRepository
            .findByCode(code)
            .orElseThrow(() -> new NotFoundException(RESERVATION_NOT_FOUND));

    return modelMapper.map(reservation, ReservationDTO.class);
  }

  public PaginationDTO<ReservationDTO> getByFilter(ReservationFilterDTO filter) {
    var result = reservationRepository.findByFilter(filter);
    var nextToken =
        getNextToken(
            result,
            filter.getRows(),
            ReservationDTO::getId,
            reservation -> filter.getOrderByColumn().getColumnValue(reservation));

    return new PaginationDTO<>(result, nextToken);
  }
}
