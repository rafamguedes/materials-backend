package com.materials.api.controller.dto;

import com.materials.api.enums.FilterOrderEnum;
import com.materials.api.enums.OrderByColumnUserEnum;
import com.materials.api.pagination.PaginationFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UserFilterDTO extends PaginationFilter {
  private FilterOrderEnum order;
  private String search;
  private OrderByColumnUserEnum orderByColumn;
}
