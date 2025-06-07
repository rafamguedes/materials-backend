package com.materials.api.controller.dto;

import com.materials.api.enums.FilterOrderEnum;
import com.materials.api.enums.ItemStatusEnum;
import com.materials.api.enums.OrderByColumnItemEnum;
import com.materials.api.enums.OrderByColumnReservationEnum;
import com.materials.api.enums.ReservationStatusEnum;
import com.materials.api.pagination.PagedFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ItemFilterDTO extends PagedFilter {
  private FilterOrderEnum order;
  private String search;
  private OrderByColumnItemEnum orderByColumn;
  private ItemStatusEnum status;
}
