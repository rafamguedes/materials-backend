package com.materials.api.controller;

import com.materials.api.controller.dto.ReservationFilterDTO;
import com.materials.api.controller.dto.ReservationRequestDTO;
import com.materials.api.controller.dto.ReservationUpdateRequestDTO;
import com.materials.api.pagination.PaginationDTO;
import com.materials.api.service.ReservationService;
import com.materials.api.service.dto.ReservationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservations")
@Tag(name = "Reservations", description = "Endpoints for managing reservations in the application")
public class ReservationController {
  private final ReservationService reservationService;

  @PostMapping
  @Operation(description = "Creates a new reservation in the application.")
  public ResponseEntity<ReservationDTO> create(
      @Valid @RequestBody ReservationRequestDTO requestDTO) {
    var response = reservationService.create(requestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/{id}")
  @Operation(description = "Retrieves a reservation by its ID.")
  public ResponseEntity<ReservationDTO> getById(@PathVariable Long id) {
    var response = reservationService.getById(id);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping("/code/{code}")
  @Operation(description = "Retrieves a reservation by its code.")
  public ResponseEntity<ReservationDTO> getByCode(@PathVariable String code) {
    var response = reservationService.getByCode(code);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping
  @Operation(description = "Retrieves a paginated list of reservations based on the provided filter.")
  public ResponseEntity<PaginationDTO<ReservationDTO>> getByFilter(ReservationFilterDTO filterDTO) {
    var response = reservationService.getByFilter(filterDTO);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PutMapping("/{id}")
  @Operation(description = "Updates an existing reservation by its ID.")
  public ResponseEntity<Void> update(
      @PathVariable Long id, @Valid @RequestBody ReservationUpdateRequestDTO requestDTO) {
    reservationService.update(id, requestDTO);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  @Operation(description = "Deletes a reservation by its ID.")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    reservationService.hardDelete(id);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/soft-softDelete/{id}")
  @Operation(description = "Soft deletes a reservation by its ID.")
  public ResponseEntity<Void> softDelete(@PathVariable Long id) {
    reservationService.softDelete(id);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/recover-softDelete/{id}")
  @Operation(description = "Recovers a soft-deleted reservation by its ID.")
  public ResponseEntity<Void> recover(@PathVariable Long id) {
    reservationService.recoverDelete(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/cancel/{code}")
  @Operation(description = "Cancels a reservation by its code.")
  public ResponseEntity<Void> cancel(@PathVariable String code) {
    reservationService.cancel(code);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/start/{code}")
  @Operation(description = "Starts a reservation by its code.")
  public ResponseEntity<Void> start(@PathVariable String code) {
    reservationService.start(code);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/complete/{code}")
  @Operation(description = "Completes a reservation by its code.")
  public ResponseEntity<Void> complete(@PathVariable String code) {
    reservationService.complete(code);
    return ResponseEntity.ok().build();
  }
}
