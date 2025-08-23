package com.materials.api.service;

import static com.materials.api.service.exceptions.messages.ItemMessages.*;
import static com.materials.api.utils.GenerateSerialNumber.generateSerialNumber;
import static com.materials.api.pagination.TokenGenerator.generateNextPageToken;

import com.materials.api.controller.dto.ItemFilterDTO;
import com.materials.api.controller.dto.ItemRequestDTO;
import com.materials.api.entity.Item;
import com.materials.api.enums.ItemStatusEnum;
import com.materials.api.pagination.PaginationDTO;
import com.materials.api.repository.ItemRepository;
import com.materials.api.service.dto.ItemDTO;

import com.materials.api.service.exceptions.GeneralException;
import com.materials.api.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

  private final ItemRepository itemRepository;
  private final ModelMapper modelMapper;

  @CacheEvict(value = "items-filter", allEntries = true)
  public ItemDTO create(ItemRequestDTO requestDTO) {
    var item = modelMapper.map(requestDTO, Item.class);
    item.setStatus(ItemStatusEnum.AVAILABLE);
    item.setSerialNumber(generateSerialNumber());

    return modelMapper.map(itemRepository.save(item), ItemDTO.class);
  }

  @Cacheable(
      cacheNames  = "items",
      key = "#id")
  public ItemDTO getById(Long id) {
    log.info("Fetching item with id: {}", id);
    var item = itemRepository.findById(id).orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND + id));
    return modelMapper.map(item, ItemDTO.class);
  }

  @Cacheable(
      cacheNames = "items-filter",
      key = "'rows:' + #filter.rows + '|order:' + #filter.order + '|orderByColumn:' + #filter.orderByColumn + '|status:' + #filter.status")
  public PaginationDTO<ItemDTO> findByFilter(ItemFilterDTO filter) {
    log.info("Finding items with filter: {}", filter);
    var result = itemRepository.findByFilter(filter);
    var nextToken =
        generateNextPageToken(
            result,
            filter.getRows(),
            ItemDTO::getId,
            item -> filter.getOrderByColumn().getColumnValue(item));

    return new PaginationDTO<>(result, nextToken);
  }

  @Caching(
      put = {@CachePut(cacheNames = "items", key = "#id")},
      evict = {@CacheEvict(cacheNames = "items-filter", allEntries = true)})
  public ItemDTO update(Long id, ItemRequestDTO requestDTO) {
    var item = itemRepository.findById(id).orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND));
    item.setName(requestDTO.getName());
    item.setDescription(requestDTO.getDescription());
    item.setType(requestDTO.getType());

    modelMapper.map(requestDTO, item);
    return modelMapper.map(itemRepository.save(item), ItemDTO.class);
  }

  @Caching(
      evict = {
        @CacheEvict(cacheNames = "items", key = "#id"),
        @CacheEvict(cacheNames = "items-filter", allEntries = true)
      })
  public void delete(Long id) {
    var item = itemRepository.findById(id).orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND));
    try {
      itemRepository.delete(item);
    } catch (org.springframework.dao.DataIntegrityViolationException e) {
      throw new GeneralException(ITEM_DELETE_ERROR_MESSAGE);
    }
  }
}
