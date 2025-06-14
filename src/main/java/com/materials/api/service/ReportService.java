package com.materials.api.service;

import com.materials.api.controller.dto.UserReportFilterDTO;
import com.materials.api.entity.User;
import com.materials.api.repository.UserRepository;
import com.materials.api.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {

  private final UserRepository userRepository;
  private final PdfReportService pdfReportService;

  public byte[] generateInactiveUsersReport() {
    List<User> inactiveUsers = userRepository.findByActiveFalse();
    if (inactiveUsers.isEmpty()) {
        throw new RuntimeException("Nenhum usuário inativo encontrado.");
    }
    Map<String, Object> data = Map.of("users", inactiveUsers);

    return pdfReportService.generatePdf("reports/inactive_users_report", data);
  }
}
