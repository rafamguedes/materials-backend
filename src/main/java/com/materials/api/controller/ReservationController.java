package com.materials.api.controller;

import com.materials.api.controller.dto.ReservationFilterDTO;
import com.materials.api.controller.dto.ReservationRequestDTO;
import com.materials.api.controller.dto.ReservationUpdateRequestDTO;
import com.materials.api.pagination.PagedDTO;
import com.materials.api.service.ReservationService;
import com.materials.api.service.dto.ReservationDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservations")
public class ReservationController {
  private final ReservationService reservationService;

  @PostMapping
  public ResponseEntity<ReservationDTO> create(
      @Valid @RequestBody ReservationRequestDTO requestDTO) {
    var response = reservationService.create(requestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ReservationDTO> getById(@PathVariable Long id) {
    var response = reservationService.getById(id);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping("/code/{code}")
  public ResponseEntity<ReservationDTO> getByCode(@PathVariable String code) {
    var response = reservationService.getByCode(code);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping
  public ResponseEntity<PagedDTO<ReservationDTO>> getByFilter(ReservationFilterDTO filterDTO) {
    var response = reservationService.getByFilter(filterDTO);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(
      @PathVariable Long id, @Valid @RequestBody ReservationUpdateRequestDTO requestDTO) {
    reservationService.update(id, requestDTO);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    reservationService.hardDelete(id);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/soft-delete/{id}")
  public ResponseEntity<Void> softDelete(@PathVariable Long id) {
    reservationService.softDelete(id);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/recover-delete/{id}")
  public ResponseEntity<Void> recover(@PathVariable Long id) {
    reservationService.recoverDelete(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/cancel/{id}")
  public ResponseEntity<Void> cancel(@PathVariable Long id) {
    reservationService.cancel(id);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/start/{id}")
  public ResponseEntity<Void> start(@PathVariable Long id) {
    reservationService.start(id);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/complete/{id}")
  public ResponseEntity<Void> complete(@PathVariable Long id) {
    reservationService.complete(id);
    return ResponseEntity.ok().build();
  }
}
