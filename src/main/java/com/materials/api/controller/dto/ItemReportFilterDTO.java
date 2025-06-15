package com.materials.api.controller.dto;

import com.materials.api.enums.FilterOrderEnum;
import com.materials.api.enums.ItemStatusEnum;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class ItemReportFilterDTO {
  private String startDate;
  private String endDate;
  private List<ItemStatusEnum> status;
  private FilterOrderEnum orderBy;
}
