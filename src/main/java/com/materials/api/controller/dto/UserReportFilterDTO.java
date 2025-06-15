package com.materials.api.controller.dto;

import com.materials.api.enums.FilterOrderEnum;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UserReportFilterDTO {
  private String startDate;
  private String endDate;
  private Boolean active;
  private FilterOrderEnum orderBy;
}
