package com.materials.api.service;

import static com.materials.api.utils.GenerateCode.generateCode;
import static com.materials.api.utils.TokenUtils.getNextToken;

import com.materials.api.controller.dto.ReservationFilterDTO;
import com.materials.api.controller.dto.ReservationRequestDTO;
import com.materials.api.controller.dto.ReservationUpdateRequestDTO;
import com.materials.api.entity.Item;
import com.materials.api.entity.Reservation;
import com.materials.api.entity.User;
import com.materials.api.enums.ItemStatusEnum;
import com.materials.api.enums.ReservationStatusEnum;
import com.materials.api.service.exceptions.BadRequestException;
import com.materials.api.pagination.PaginationDTO;
import com.materials.api.repository.ItemRepository;
import com.materials.api.repository.ReservationRepository;
import com.materials.api.repository.UserRepository;
import com.materials.api.service.dto.ReservationDTO;
import com.materials.api.service.exceptions.NotFoundException;
import com.materials.api.utils.EmailTemplateUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {
  private static final String RESERVATION_NOT_FOUND = "Reserva não encontrada";
  private static final String ITEM_NOT_FOUND = "Item não encontrado, verifique o ID do item: ";
  private static final String EMAIL_SUBJECT = "Confirmação de Reserva";
  private static final String USER_NOT_FOUND =
      "Usuário não encontrado, verifique o ID do usuário: ";
  private static final String RESERVATION_ALREADY_EXISTS =
      "Já existe uma reserva para o horário informado: ";
  private static final String RESERVATION_START_VALIDATE_STATUS =
      "A reserva só pode ser iniciada se estiver com o status Pendente.";
  public static final String RESERVATION_COMPLETE_VALIDATE_STATUS =
      "A reserva só pode ser concluída se estiver em Andamento.";
  public static final String RESERVATION_CANCEL_VALIDATE_STATUS =
      "A reserva só pode ser cancelada se estiver com o status Pendente.";

  private final ReservationRepository reservationRepository;
  private final UserRepository userRepository;
  private final ItemRepository itemRepository;
  private final ModelMapper modelMapper;
  private final SendGridEmailService emailService;

  public ReservationDTO create(ReservationRequestDTO requestDTO) {
    validateDateTime(requestDTO);

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
    sendEmailConfirmation(user, item, savedReservation);
    return modelMapper.map(savedReservation, ReservationDTO.class);
  }

  private void sendEmailConfirmation(User user, Item item, Reservation reservation) {
    var emailContent = EmailTemplateUtils.getReservationConfirmationEmail(user, item, reservation);
    emailService.sendEmail(user.getEmail(), EMAIL_SUBJECT, emailContent);
  }

  private void validateDateTime(ReservationRequestDTO requestDTO) {
    reservationRepository
        .findByDateTime(requestDTO.getDateTime())
        .ifPresent(
            reservation -> {
              throw new BadRequestException(RESERVATION_ALREADY_EXISTS + requestDTO.getDateTime());
            });
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
    reservationRepository.save(reservation);
  }

  public void complete(String code) {
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

    reservationRepository.save(reservation);
  }
}
