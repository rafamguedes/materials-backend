package com.materials.api.service;

import static com.materials.api.utils.GenerateCode.generateCode;

import com.materials.api.controller.dto.ReservationFilterDTO;
import com.materials.api.controller.dto.ReservationRequestDTO;
import com.materials.api.controller.dto.ReservationUpdateRequestDTO;
import com.materials.api.entity.Reservation;
import com.materials.api.enums.ItemStatusEnum;
import com.materials.api.enums.ReservationStatusEnum;
import com.materials.api.utils.TokenHelper;
import com.materials.api.pagination.PagedDTO;
import com.materials.api.repository.ItemRepository;
import com.materials.api.repository.ReservationRepository;
import com.materials.api.repository.UserRepository;
import com.materials.api.service.dto.ReservationDTO;
import com.materials.api.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {
  private static final String RESERVATION_NOT_FOUND = "Reserva não encontrada";
  private static final String USER_NOT_FOUND = "Usuário não encontrado, verifique o ID do usuário: ";
  private static final String ITEM_NOT_FOUND = "Item não encontrado, verifique o ID do item: ";

  private final ReservationRepository reservationRepository;
  private final UserRepository userRepository;
  private final ItemRepository itemRepository;
  private final ModelMapper modelMapper;

  public ReservationDTO create(ReservationRequestDTO requestDTO) {
    var user =
        userRepository
            .findById(requestDTO.getUserId())
            .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND + requestDTO.getUserId()));

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
    return modelMapper.map(savedReservation, ReservationDTO.class);
  }

  public void update(Long id, ReservationUpdateRequestDTO requestDTO) {
    var reservation =
        reservationRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(RESERVATION_NOT_FOUND));

    var item =
        itemRepository
            .findById(requestDTO.getItemId())
            .orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND + requestDTO.getItemId()));

    reservation.setDateTime(requestDTO.getDateTime());
    reservation.setItem(item);

    reservationRepository.save(reservation);
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

  @Cacheable("reservationFilters")
  public PagedDTO<ReservationDTO> getByFilter(ReservationFilterDTO filter) {
    var result = reservationRepository.findByFilter(filter);

    String nextToken = null;
    if (result.size() == filter.getRows()) {
      nextToken =
          Optional.ofNullable(CollectionUtils.lastElement(result))
              .map(dto -> TokenHelper.generateBase32Token(
                      dto.getId(), filter.getOrderByColumn().getColumnValue(dto)))
              .orElse(null);
    }

    return new PagedDTO<>(result, nextToken);
  }

  public void cancel(Long id) {
    var reservation =
        reservationRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(RESERVATION_NOT_FOUND));

    var item = itemRepository.findById(reservation.getItem().getId())
        .orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND + reservation.getItem().getId()));

    reservation.setStatus(ReservationStatusEnum.CANCELED);
    item.setStatus(ItemStatusEnum.AVAILABLE);
    reservationRepository.save(reservation);
  }

  public void start(Long id) {
    var reservation =
        reservationRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(RESERVATION_NOT_FOUND));

    var item = itemRepository.findById(reservation.getItem().getId())
        .orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND + reservation.getItem().getId()));

    reservation.setStatus(ReservationStatusEnum.IN_PROGRESS);
    reservation.setStartTime(LocalDateTime.now());
    item.setStatus(ItemStatusEnum.RESERVED);
    reservationRepository.save(reservation);
  }

  public void complete(Long id) {
    var reservation =
        reservationRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(RESERVATION_NOT_FOUND));

    var item = itemRepository.findById(reservation.getItem().getId())
        .orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND + reservation.getItem().getId()));

    reservation.setStatus(ReservationStatusEnum.COMPLETED);
    reservation.setEndTime(LocalDateTime.now());
    item.setStatus(ItemStatusEnum.AVAILABLE);
    reservationRepository.save(reservation);
  }
}
