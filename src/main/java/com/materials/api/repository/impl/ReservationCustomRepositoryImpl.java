package com.materials.api.repository.impl;

import com.materials.api.controller.dto.ReservationFilterDTO;
import com.materials.api.controller.dto.ReservationReportFilterDTO;
import com.materials.api.entity.Reservation;
import com.materials.api.enums.FilterOrderEnum;
import com.materials.api.enums.ReservationStatusEnum;
import com.materials.api.utils.TokenUtils;
import com.materials.api.repository.ReservationCustomRepository;
import com.materials.api.service.dto.ReservationDTO;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class ReservationCustomRepositoryImpl implements ReservationCustomRepository {
  private final EntityManager entityManager;

  @Override
  public List<ReservationDTO> findByFilter(ReservationFilterDTO filter) {
    var nativeQuery =
        entityManager
            .createNativeQuery(
                "SELECT "
                    + "r.id, "
                    + "r.date_time AS dateTime, "
                    + "r.start_time AS startTime, "
                    + "r.end_time AS endTime, "
                    + "r.code, "
                    + "r.status, "
                    + "r.created_at AS createdAt, "
                    + "u.registry AS userRegistry, "
                    + "i.id AS itemId, "
                    + "i.item_type AS itemType "
                    + "FROM tb_reservation r "
                    + "INNER JOIN tb_users u ON r.user_id = u.id "
                    + "INNER JOIN tb_item i ON r.item_id = i.id "
                    + "WHERE 1=1 "
                    + appendSearchByFilter(filter)
                    + appendSearchByStatus(filter)
                    + appendTokenFilter(filter)
                    + "ORDER BY " + filter.getOrderByColumn()
                    + (FilterOrderEnum.DESC.equals(filter.getOrder())
                        ? " DESC, r.id DESC"
                        : " ASC, r.id ASC"),
                Reservation.RESERVATION_DTO_MAPPING)
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

  private String appendSearchByFilter(ReservationFilterDTO filter) {
    return Objects.nonNull(filter.getSearch())
        ? " AND (CAST(r.id as VARCHAR) LIKE :search"
            + " OR r.code ILIKE :search"
            + " OR r.status ILIKE :search"
            + " OR r.date_time::text ILIKE :search"
            + " OR u.registry ILIKE :search"
            + " OR i.item_type ILIKE :search) "
        : " ";
  }

  private String appendSearchByStatus(ReservationFilterDTO filter) {
    return Objects.nonNull(filter.getStatus())
        ? " AND r.status LIKE CAST(:status AS VARCHAR) "
        : " ";
  }

  private String appendTokenFilter(ReservationFilterDTO filter) {
    var orderOperation = FilterOrderEnum.DESC.equals(filter.getOrder()) ? "<" : ">";
    var column = filter.getOrderByColumn().getColumnName();
    var tokenName = filter.getOrderByColumn().getTokenName();

    return Objects.nonNull(filter.getNextToken())
        ? "AND (" + column + ", r.id) " + orderOperation + " (" + tokenName + ", :tokenId) "
        : " ";
  }

  @Override
  public List<Reservation> generateReservationReport(ReservationReportFilterDTO filter) {
    var sql =
        "SELECT r.* "
            + "FROM tb_reservation r "
            + "WHERE 1=1 "
            + "AND r.status in (:status) "
            + "AND r.date_time BETWEEN TO_DATE(:startDate, 'YYYY-MM-DD') AND TO_DATE(:endDate, 'YYYY-MM-DD') "
            + "ORDER BY r.id " + Optional.ofNullable(filter.getOrderBy()).orElse(FilterOrderEnum.DESC);

    var query = entityManager.createNativeQuery(sql, Reservation.class);
    query.setParameter("startDate", filter.getStartDate());
    query.setParameter("endDate", filter.getEndDate());

    Optional.ofNullable(filter.getStatus())
        .ifPresentOrElse(status -> query.setParameter("status", status.stream().map(ReservationStatusEnum::name).toList()),
            () -> query.setParameter("status", Stream.of(ReservationStatusEnum.values()).map(ReservationStatusEnum::name).toList()));

    return query.getResultList();
  }
}
