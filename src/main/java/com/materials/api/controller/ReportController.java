package com.materials.api.controller;

import com.materials.api.controller.dto.UserReportFilterDTO;
import com.materials.api.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
public class ReportController {

  private final ReportService reportService;

  @GetMapping("/inactive-users")
  public ResponseEntity<byte[]> getInactiveUsersReport() {
    byte[] pdf = reportService.generateInactiveUsersReport();

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=inactive_users_report.pdf")
        .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
        .body(pdf);
  }
}
