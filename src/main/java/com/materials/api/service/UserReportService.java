package com.materials.api.service;

import com.materials.api.controller.dto.UserReportFilterDTO;
import com.materials.api.entity.User;
import com.materials.api.repository.UserRepository;
import com.materials.api.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserReportService {

  private final UserRepository userRepository;
  private final ReportService reportService;

  public byte[] generateInactiveUsersReport(UserReportFilterDTO filter) {
    List<User> inactiveUsers = userRepository.findInactiveUsers(filter);
    if (inactiveUsers.isEmpty()) {
        List.of();
    }
    Map<String, Object> data = Map.of("users", inactiveUsers);

    return reportService.generateReport("reports/inactive_users_report", data);
  }
}
