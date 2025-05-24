package com.materials.api.repository.impl;

import com.materials.api.controller.dto.ReservationFilterDTO;
import com.materials.api.entity.Reservation;
import com.materials.api.enums.FilterOrderEnum;
import com.materials.api.utils.TokenHelper;
import com.materials.api.repository.ReservationCustomRepository;
import com.materials.api.service.dto.ReservationDTO;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class ReservationCustomRepositoryImpl implements ReservationCustomRepository {
  private final EntityManager entityManager;

  @Override
  public List<ReservationDTO> findByFilter(ReservationFilterDTO filter) {
    var nativeQuery =
        entityManager
            .createNativeQuery(
                "SELECT r.id, r.date_time as dateTime, r.code, r.status, u.registry as userRegistry, i.item_type as itemType "
                    + "FROM tb_reservation r "
                    + "INNER JOIN tb_users u ON r.user_id = u.id "
                    + "INNER JOIN tb_item i ON r.item_id = i.id "
                    + "WHERE 1=1 "
                    + setSearchFilter(filter)
                    + setTokenFilter(filter)
                    + "ORDER BY " + filter.getOrderByColumn()
                    + (FilterOrderEnum.DESC.equals(filter.getOrder())
                        ? " DESC, r.id DESC"
                        : " ASC, r.id ASC"),
                Reservation.RESERVATION_DTO_MAPPING)
            .setMaxResults(filter.getRows());

    Optional.ofNullable(filter.getSearch())
        .ifPresent(s -> nativeQuery.setParameter("search", "%" + s + "%"));

    Optional.ofNullable(filter.getNextToken())
        .ifPresent(
            t -> {
              nativeQuery.setParameter("tokenName", TokenHelper.extractFieldFromToken(t));
              nativeQuery.setParameter("tokenId", TokenHelper.extractIdFromToken(t));
            });

    return nativeQuery.getResultList();
  }

  private String setSearchFilter(ReservationFilterDTO filter) {
    return Objects.nonNull(filter.getSearch())
        ? " AND (CAST(r.id as VARCHAR) LIKE :search"
            + " OR r.code ILIKE :search"
            + " OR r.status ILIKE :search"
            + " OR r.date_time::text ILIKE :search"
            + " OR u.registry ILIKE :search"
            + " OR i.item_type ILIKE :search) "
        : " ";
  }

  private String setTokenFilter(ReservationFilterDTO filter) {
    var orderOperation = FilterOrderEnum.DESC.equals(filter.getOrder()) ? "<" : ">";
    var column = filter.getOrderByColumn().getColumnName();
    var tokenName = filter.getOrderByColumn().getTokenName();

    return Objects.nonNull(filter.getNextToken())
        ? "AND (" + column + ", r.id) " + orderOperation + " (" + tokenName + ", :tokenId) "
        : " ";
  }
}
