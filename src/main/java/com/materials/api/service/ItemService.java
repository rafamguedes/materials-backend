package com.materials.api.service;

import static com.materials.api.utils.GenerateSerialNumber.generateSerialNumber;

import com.materials.api.controller.dto.ItemRequestDTO;
import com.materials.api.entity.Item;
import com.materials.api.enums.ItemStatusEnum;
import com.materials.api.repository.ItemRepository;
import com.materials.api.service.dto.ItemDTO;

import com.materials.api.service.exceptions.GeneralException;
import com.materials.api.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

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

  public Iterable<ItemDTO> getAll() {
    var items = itemRepository.findAll();
    return modelMapper.map(items, new org.modelmapper.TypeToken<Iterable<ItemDTO>>() {}.getType());
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
    itemRepository.delete(item);
  }
}
