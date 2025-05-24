package com.materials.api.controller.dto;

import com.materials.api.enums.FilterOrderEnum;
import com.materials.api.enums.OrderByColumnReservationEnum;
import com.materials.api.pagination.PagedFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper = true)
public class ReservationFilterDTO extends PagedFilter {
  private FilterOrderEnum order;
  private String search;
  private OrderByColumnReservationEnum orderByColumn;
}

