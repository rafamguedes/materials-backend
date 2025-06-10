package com.materials.api.enums;

import java.util.function.Function;

import com.materials.api.service.dto.UserDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderByColumnUserEnum {
  ID("u.id", "CAST(:tokenName AS BIGINT)", dto -> dto.getId().toString()),
  NAME("u.name", ":tokenName", UserDTO::getName);

  private final String columnName;
  private final String tokenName;

  private final Function<UserDTO, String> value;

  public String getColumnValue(UserDTO dto) {
    return value.apply(dto);
  }
}
