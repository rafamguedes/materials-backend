package com.materials.api.service.reports;

import com.materials.api.controller.dto.ItemReportFilterDTO;
import com.materials.api.entity.Item;
import com.materials.api.repository.ItemRepository;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemReportService {

  private final ItemRepository itemRepository;
  private final ReportService reportService;

  public byte[] generateItemReport(ItemReportFilterDTO filter) {
    List<Item> itemList = itemRepository.generateItemReport(filter);
    if (itemList.isEmpty()) {
        List.of();
    }
    Map<String, Object> data = Map.of("items", itemList);

    return reportService.generateReport("reports/items_report", data);
  }
}
