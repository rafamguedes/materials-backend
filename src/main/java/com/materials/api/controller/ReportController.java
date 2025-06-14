package com.materials.api.controller;

import com.materials.api.controller.dto.UserReportFilterDTO;
import com.materials.api.service.UserReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/report")
public class ReportController {

  private final UserReportService userReportService;

  @GetMapping("/inactive-users")
  public ResponseEntity<byte[]> getInactiveUsersReport(UserReportFilterDTO filter) {
    byte[] pdf = userReportService.generateInactiveUsersReport(filter);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=inactive_users_report.pdf")
        .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
        .body(pdf);
  }
}
