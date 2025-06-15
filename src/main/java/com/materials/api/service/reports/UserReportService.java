package com.materials.api.service.reports;

import com.materials.api.controller.dto.UserReportFilterDTO;
import com.materials.api.entity.User;
import com.materials.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserReportService {

  private final UserRepository userRepository;
  private final ReportService reportService;

  public byte[] generateUsersReport(UserReportFilterDTO filter) {
    List<User> inactiveUsers = userRepository.generateUsersReport(filter);
    if (inactiveUsers.isEmpty()) {
        List.of();
    }
    Map<String, Object> data = Map.of("users", inactiveUsers);

    return reportService.generateReport("reports/users_report", data);
  }
}
