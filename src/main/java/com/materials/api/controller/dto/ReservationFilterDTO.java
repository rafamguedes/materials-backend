package com.materials.api.controller.dto;

import com.materials.api.enums.FilterOrderEnum;
import com.materials.api.pagination.PagedFilter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationFilterDTO extends PagedFilter {
  private FilterOrderEnum order;
  private String search;
}

