package com.materials.api.controller.dto;

import com.materials.api.enums.FilterOrderEnum;
import com.materials.api.enums.ReservationStatusEnum;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class ReservationReportFilterDTO {
  private String startDate;
  private String endDate;
  private List<ReservationStatusEnum> status;
  private FilterOrderEnum orderBy;
}
