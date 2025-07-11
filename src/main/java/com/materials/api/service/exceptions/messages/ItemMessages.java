package com.materials.api.service.exceptions.messages;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ItemMessages {
  public static final String ITEM_NOT_FOUND =
      "Item not found, please check the item ID: ";
  public static final String ITEM_DELETE_ERROR_MESSAGE =
      "Error deleting item, please check if the item is associated with any reservations.";
}
