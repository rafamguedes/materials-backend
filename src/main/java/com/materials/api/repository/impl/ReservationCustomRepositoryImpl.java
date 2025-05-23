package com.materials.api.repository.impl;

import com.materials.api.controller.dto.ReservationFilterDTO;
import com.materials.api.entity.Reservation;
import com.materials.api.enums.FilterOrderEnum;
import com.materials.api.pagination.PagedHelper;
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
                    + "ORDER BY r.code "
                    + setFilterOrder(filter)
                    + ", r.id "
                    + setFilterOrder(filter),
                Reservation.RESERVATION_DTO_MAPPING)
            .setMaxResults(filter.getRows());

    Optional.ofNullable(filter.getSearch())
        .ifPresent(s -> nativeQuery.setParameter("search", "%" + s + "%"));

    Optional.ofNullable(filter.getNextToken())
        .ifPresent(
            t -> {
              nativeQuery.setParameter("tokenName", PagedHelper.tokenToName(t));
              nativeQuery.setParameter("tokenId", PagedHelper.tokenToId(t));
            });

    return nativeQuery.getResultList();
  }

  private FilterOrderEnum setFilterOrder(ReservationFilterDTO filter) {
    return Optional.ofNullable(filter.getOrder()).orElse(FilterOrderEnum.ASC);
  }

  private String setSearchFilter(ReservationFilterDTO filter) {
    return Objects.nonNull(filter.getSearch())
        ? " AND (CAST(r.id as VARCHAR) LIKE :search"
            + " OR r.code ILIKE :search"
            + " OR r.status ILIKE :search) "
        : " ";
  }

  private String setTokenFilter(ReservationFilterDTO filter) {
    var orderOperation = FilterOrderEnum.DESC.equals(filter.getOrder()) ? "<" : ">";
    return Objects.nonNull(filter.getNextToken())
        ? "AND (r.code, r.id) " + orderOperation + " (:tokenName, :tokenId) "
        : " ";
  }
}
