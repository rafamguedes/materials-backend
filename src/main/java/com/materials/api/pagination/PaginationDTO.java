package com.materials.api.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationDTO<T extends Serializable> implements Serializable {
  private List<T> data;
  private String nextToken;
}
