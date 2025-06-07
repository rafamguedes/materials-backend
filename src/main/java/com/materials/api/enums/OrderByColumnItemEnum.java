package com.materials.api.enums;

import com.materials.api.service.dto.ItemDTO;
import com.materials.api.service.dto.ReservationDTO;
import java.util.function.Function;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderByColumnItemEnum {
  ID("i.id", "CAST(:tokenName AS BIGINT)", dto -> dto.getId().toString()),
  NAME("i.name", ":tokenName", ItemDTO::getName),
  DESCRIPTION("i.description", ":tokenName", ItemDTO::getDescription),
  ITEM_TYPE("i.item_type", ":tokenName", ItemDTO::getItemType),
  STATUS("i.status", ":tokenName", ItemDTO::getStatus),
  SERIAL_NUMBER("i.serial_number", ":tokenName", ItemDTO::getSerialNumber);

  private final String columnName;
  private final String tokenName;

  private final Function<ItemDTO, String> value;

  public String getColumnValue(ItemDTO dto) {
    return value.apply(dto);
  }
}
