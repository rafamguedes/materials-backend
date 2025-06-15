package com.materials.api.repository;

import com.materials.api.controller.dto.ItemFilterDTO;
import com.materials.api.controller.dto.ItemReportFilterDTO;
import com.materials.api.entity.Item;
import com.materials.api.service.dto.ItemDTO;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCustomRepository {
  List<ItemDTO> findByFilter(ItemFilterDTO filter);
  List<Item> generateItemReport(ItemReportFilterDTO filter);
}
