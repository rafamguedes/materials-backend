package com.materials.api.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PagedDTO<T extends Serializable> extends PagedListDTO<T> {
  private String nextToken;

  public PagedDTO(List<T> data, String nextToken) {
    super(data);
    this.nextToken = nextToken;
  }
}
