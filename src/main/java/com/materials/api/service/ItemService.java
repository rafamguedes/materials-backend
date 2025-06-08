package com.materials.api.service;

import static com.materials.api.utils.GenerateSerialNumber.generateSerialNumber;

import com.materials.api.controller.dto.ItemFilterDTO;
import com.materials.api.controller.dto.ItemRequestDTO;
import com.materials.api.controller.dto.ReservationFilterDTO;
import com.materials.api.entity.Item;
import com.materials.api.enums.ItemStatusEnum;
import com.materials.api.pagination.PagedDTO;
import com.materials.api.repository.ItemRepository;
import com.materials.api.service.dto.ItemDTO;

import com.materials.api.service.dto.ReservationDTO;
import com.materials.api.service.exceptions.GeneralException;
import com.materials.api.service.exceptions.NotFoundException;
import com.materials.api.utils.TokenHelper;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.util.CollectionUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
  private static final String ITEM_NOT_FOUND = "Item not found";

  private final ItemRepository itemRepository;
  private final ModelMapper modelMapper;

  public ItemDTO create(ItemRequestDTO requestDTO) {
    var item = modelMapper.map(requestDTO, Item.class);
    item.setStatus(ItemStatusEnum.AVAILABLE);
    item.setSerialNumber(generateSerialNumber());

    return modelMapper.map(itemRepository.save(item), ItemDTO.class);
  }

  public ItemDTO getById(Long id) {
    var item = itemRepository.findById(id).orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND));
    return modelMapper.map(item, ItemDTO.class);
  }

  public PagedDTO<ItemDTO> findByFilter(ItemFilterDTO filter) {
    var result = itemRepository.findByFilter(filter);

    String nextToken = null;
    if (result.size() == filter.getRows()) {
      nextToken =
          Optional.ofNullable(CollectionUtils.lastElement(result))
              .map(
                  dto ->
                      TokenHelper.generateBase32Token(
                          dto.getId(), filter.getOrderByColumn().getColumnValue(dto)))
              .orElse(null);
    }

    return new PagedDTO<>(result, nextToken);
  }

  public ItemDTO update(Long id, ItemRequestDTO requestDTO) {
    var item = itemRepository.findById(id).orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND));
    item.setName(requestDTO.getName());
    item.setDescription(requestDTO.getDescription());
    item.setType(requestDTO.getType());

    modelMapper.map(requestDTO, item);
    return modelMapper.map(itemRepository.save(item), ItemDTO.class);
  }

  public void delete(Long id) {
    var item = itemRepository.findById(id).orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND));
    try {
      itemRepository.delete(item);
    } catch (org.springframework.dao.DataIntegrityViolationException e) {
      throw new GeneralException("Não é possível deletar um equipamento que esteja vinculado a uma reserva.");
    }
  }
}
