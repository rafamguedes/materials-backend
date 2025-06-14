package com.materials.api.controller.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UserReportFilterDTO {
  private String startDate;
  private String endDate;
}
