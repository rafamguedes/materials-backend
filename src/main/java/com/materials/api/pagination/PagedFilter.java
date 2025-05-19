package com.materials.api.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class PagedFilter {
  private String nextToken;
  private Integer rows;

  public Integer getRows() {
    return Optional.ofNullable(rows).orElse(50);
  }
}
