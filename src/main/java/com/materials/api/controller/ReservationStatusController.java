package com.materials.api.controller;

import com.materials.api.service.ReservationStatusService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservations")
public class ReservationStatusController {

  private final ReservationStatusService reservationStatusService;

  @PatchMapping("/cancel/{code}")
  @Operation(description = "Cancels a reservation by its code.")
  public ResponseEntity<Void> cancel(@PathVariable String code) {
    reservationStatusService.cancel(code);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/start/{code}")
  @Operation(description = "Starts a reservation by its code.")
  public ResponseEntity<Void> start(@PathVariable String code) {
    reservationStatusService.start(code);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/complete/{code}")
  @Operation(description = "Completes a reservation by its code.")
  public ResponseEntity<Void> complete(@PathVariable String code) {
    reservationStatusService.finish(code);
    return ResponseEntity.ok().build();
  }
}
