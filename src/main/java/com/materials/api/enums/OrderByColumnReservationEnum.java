package com.materials.api.enums;

import com.materials.api.service.dto.ReservationDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@Getter
@RequiredArgsConstructor
public enum OrderByColumnReservationEnum {
  ID("r.id", "CAST(:tokenName AS BIGINT)", dto -> dto.getId().toString()),
  CODE("r.code", ":tokenName", ReservationDTO::getCode),
  DATE_TIME("r.date_time", "CAST(:tokenName AS TIMESTAMP)", dto -> dto.getDateTime().toString()),
  STATUS("r.status", ":tokenName", ReservationDTO::getStatus),
  REGISTRY("u.registry", ":tokenName", ReservationDTO::getUserRegistry),
  ITEM_TYPE("i.item_type", ":tokenName", ReservationDTO::getItemType);

  private final String columnName;
  private final String tokenName;

  private final Function<ReservationDTO, String> value;

  public String getColumnValue(ReservationDTO dto) {
    return value.apply(dto);
  }
}
