package com.materials.api.controller;

import com.materials.api.controller.dto.ReservationReportFilterDTO;
import com.materials.api.controller.dto.UserReportFilterDTO;
import com.materials.api.service.reports.ReservationReportService;
import com.materials.api.service.reports.UserReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
public class ReportController {

  private final UserReportService userReportService;
  private final ReservationReportService reservationReportService;

  @GetMapping("/users")
  public ResponseEntity<byte[]> getUsersReport(UserReportFilterDTO filter) {
    byte[] pdf = userReportService.generateUsersReport(filter);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=users_report.pdf")
        .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
        .body(pdf);
  }

  @GetMapping("/reservations")
  public ResponseEntity<byte[]> getReservationsReport(ReservationReportFilterDTO filter) {
    byte[] pdf = reservationReportService.generateReservationReport(filter);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reservations_report.pdf")
        .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
        .body(pdf);
  }
}
