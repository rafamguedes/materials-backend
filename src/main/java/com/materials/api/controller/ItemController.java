package com.materials.api.controller;

import com.materials.api.controller.dto.ItemFilterDTO;
import com.materials.api.controller.dto.ItemRequestDTO;
import com.materials.api.pagination.PaginationDTO;
import com.materials.api.service.ItemService;
import com.materials.api.service.dto.ItemDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemController {
  private final ItemService itemService;

  @PostMapping
  public ResponseEntity<ItemDTO> create(@Valid @RequestBody ItemRequestDTO itemRequestDTO) {
    var response = itemService.create(itemRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ItemDTO> getById(@PathVariable Long id) {
    var item = itemService.getById(id);
    return ResponseEntity.ok(item);
  }

  @GetMapping
  public ResponseEntity<PaginationDTO<ItemDTO>> findByFilter(@Valid ItemFilterDTO filter) {
    var items = itemService.findByFilter(filter);
    return ResponseEntity.ok(items);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ItemDTO> update(
      @PathVariable Long id, @Valid @RequestBody ItemRequestDTO itemRequestDTO) {
    var item = itemService.update(id, itemRequestDTO);
    return ResponseEntity.ok(item);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    itemService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
