package com.materials.api.controller;

import com.materials.api.controller.dto.ItemReportFilterDTO;
import com.materials.api.controller.dto.ReservationReportFilterDTO;
import com.materials.api.controller.dto.UserReportFilterDTO;
import com.materials.api.service.reports.ItemReportService;
import com.materials.api.service.reports.ReservationReportService;
import com.materials.api.service.reports.UserReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
@Tag(name = "Reports", description = "Endpoints for generating various reports in the application")
public class ReportController {

  private final UserReportService userReportService;
  private final ReservationReportService reservationReportService;
  private final ItemReportService itemReportService;

  @GetMapping("/users")
  @Operation(description = "Generates a report of users based on the provided filter criteria.")
  public ResponseEntity<byte[]> getUsersReport(UserReportFilterDTO filter) {
    byte[] pdf = userReportService.generateUsersReport(filter);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=users_report.pdf")
        .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
        .body(pdf);
  }

  @GetMapping("/reservations")
  @Operation(description = "Generates a report of reservations based on the provided filter criteria.")
  public ResponseEntity<byte[]> getReservationsReport(ReservationReportFilterDTO filter) {
    byte[] pdf = reservationReportService.generateReservationReport(filter);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reservations_report.pdf")
        .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
        .body(pdf);
  }

  @GetMapping("/items")
  @Operation(description = "Generates a report of items based on the provided filter criteria.")
  public ResponseEntity<byte[]> getItemsReport(ItemReportFilterDTO filter) {
    byte[] pdf = itemReportService.generateItemReport(filter);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=items_report.pdf")
        .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
        .body(pdf);
  }
}
