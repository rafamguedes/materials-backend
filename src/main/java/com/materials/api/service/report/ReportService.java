package com.materials.api.service.report;

import java.util.Map;

public interface ReportService {
  byte[] generateReport(String templateName, Map<String, Object> data);
}
