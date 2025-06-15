package com.materials.api.repository.impl;

import com.materials.api.controller.dto.ItemFilterDTO;
import com.materials.api.entity.Item;
import com.materials.api.enums.FilterOrderEnum;
import com.materials.api.enums.OrderByColumnItemEnum;
import com.materials.api.repository.ItemCustomRepository;
import com.materials.api.service.dto.ItemDTO;
import com.materials.api.utils.TokenUtils;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class ItemCustomRepositoryImpl implements ItemCustomRepository {
  private final EntityManager entityManager;

  @Override
  public List<ItemDTO> findByFilter(ItemFilterDTO filter) {
    if (Objects.isNull(filter.getOrderByColumn())) {
      filter.setOrderByColumn(OrderByColumnItemEnum.NAME);
    }

    var nativeQuery =
        entityManager
            .createNativeQuery(
                "SELECT "
                    + "i.id, "
                    + "i.name, "
                    + "i.description, "
                    + "i.item_type AS itemType, "
                    + "i.serial_number AS serialNumber, "
                    + "i.status "
                    + "FROM tb_item i "
                    + "WHERE 1=1 "
                    + appendFilterBySearch(filter)
                    + appendFilterByStatus(filter)
                    + appendTokenFilter(filter)
                    + "ORDER BY " + filter.getOrderByColumn()
                    + (FilterOrderEnum.DESC.equals(filter.getOrder())
                        ? " DESC, i.id DESC"
                        : " ASC, i.id ASC"),
                    Item.ITEM_DTO_MAPPING)
            .setMaxResults(filter.getRows());

    Optional.ofNullable(filter.getSearch())
        .ifPresent(s -> nativeQuery.setParameter("search", "%" + s + "%"));

    Optional.ofNullable(filter.getStatus())
        .ifPresent(s -> nativeQuery.setParameter("status", s.name()));

    Optional.ofNullable(filter.getNextToken())
        .ifPresent(
            t -> {
              nativeQuery.setParameter("tokenName", TokenUtils.getTokenName(t));
              nativeQuery.setParameter("tokenId", TokenUtils.getTokenId(t));
            });

    return nativeQuery.getResultList();
  }

  private String appendFilterBySearch(ItemFilterDTO filter) {
    return Objects.nonNull(filter.getSearch())
        ? " AND (CAST(i.id as VARCHAR) LIKE :search"
            + " OR i.name ILIKE :search"
            + " OR i.description ILIKE :search"
            + " OR i.item_type ILIKE :search"
            + " OR i.status ILIKE :search) "
        : " ";
  }

  private String appendFilterByStatus(ItemFilterDTO filter) {
    return Objects.nonNull(filter.getStatus())
        ? " AND i.status LIKE CAST(:status AS VARCHAR) "
        : " ";
  }

  private String appendTokenFilter(ItemFilterDTO filter) {
    var orderOperation = FilterOrderEnum.DESC.equals(filter.getOrder()) ? "<" : ">";
    var column = filter.getOrderByColumn().getColumnName();
    var tokenName = filter.getOrderByColumn().getTokenName();

    return Objects.nonNull(filter.getNextToken())
        ? "AND (" + column + ", i.id) " + orderOperation + " (" + tokenName + ", :tokenId) "
        : " ";
  }
}
