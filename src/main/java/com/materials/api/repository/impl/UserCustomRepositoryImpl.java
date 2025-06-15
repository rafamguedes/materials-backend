package com.materials.api.repository.impl;

import com.materials.api.controller.dto.UserFilterDTO;
import com.materials.api.controller.dto.UserReportFilterDTO;
import com.materials.api.entity.User;
import com.materials.api.enums.FilterOrderEnum;
import com.materials.api.enums.OrderByColumnUserEnum;
import com.materials.api.repository.UserCustomRepository;
import com.materials.api.service.dto.UserDTO;
import com.materials.api.utils.TokenUtils;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class UserCustomRepositoryImpl implements UserCustomRepository {
  private final EntityManager entityManager;

  @Override
  public List<UserDTO> findByFilter(UserFilterDTO filter) {
    if (Objects.isNull(filter.getOrderByColumn())) {
      filter.setOrderByColumn(OrderByColumnUserEnum.NAME);
    }

    var nativeQuery =
        entityManager
            .createNativeQuery(
                "SELECT "
                    + "u.id, "
                    + "u.name, "
                    + "u.email, "
                    + "u.registry, "
                    + "u.active, "
                    + "u.is_deleted as isDeleted, "
                    + "u.created_at as createdAt, "
                    + "u.updated_at as updatedAt, "
                    + "u.deleted_at as deletedAt, "
                    + "a.id AS addressId "
                    + "FROM tb_users u "
                    + "LEFT JOIN tb_address a ON u.address_id = a.id "
                    + "WHERE 1=1 "
                    + appendFilterBySearch(filter)
                    + appendTokenFilter(filter)
                    + "ORDER BY " + filter.getOrderByColumn()
                    + (FilterOrderEnum.DESC.equals(filter.getOrder())
                        ? " DESC, u.id DESC"
                        : " ASC, u.id ASC"),
                    User.USER_DTO_MAPPING)
            .setMaxResults(filter.getRows());

    Optional.ofNullable(filter.getSearch())
        .ifPresent(s -> nativeQuery.setParameter("search", "%" + s + "%"));

    Optional.ofNullable(filter.getNextToken())
        .ifPresent(
            t -> {
              nativeQuery.setParameter("tokenName", TokenUtils.getTokenName(t));
              nativeQuery.setParameter("tokenId", TokenUtils.getTokenId(t));
            });

    return nativeQuery.getResultList();
  }

  private String appendFilterBySearch(UserFilterDTO filter) {
    return Objects.nonNull(filter.getSearch())
        ? " AND (CAST(u.id as VARCHAR) LIKE :search"
            + " OR u.name ILIKE :search"
            + " OR u.registry ILIKE :search"
            + " OR u.email ILIKE :search) "
        : " ";
  }

  private String appendTokenFilter(UserFilterDTO filter) {
    var orderOperation = FilterOrderEnum.DESC.equals(filter.getOrder()) ? "<" : ">";
    var column = filter.getOrderByColumn().getColumnName();
    var tokenName = filter.getOrderByColumn().getTokenName();

    return Objects.nonNull(filter.getNextToken())
        ? "AND (" + column + ", u.id) " + orderOperation + " (" + tokenName + ", :tokenId) "
        : " ";
  }

  @Override
  public List<User> generateUsersReport(UserReportFilterDTO filter) {
    var sql =
        "SELECT u "
            + "FROM User u "
            + "WHERE 1=1 "
            + "AND u.active = :active "
            + "AND u.createdAt BETWEEN TO_DATE(:startDate, 'YYYY-MM-DD') AND TO_DATE(:endDate, 'YYYY-MM-DD') "
            + "ORDER BY u.id " + Optional.ofNullable(filter.getOrderBy()).orElse(FilterOrderEnum.DESC);

    var query = entityManager.createQuery(sql, User.class);
    query.setParameter("startDate", filter.getStartDate());
    query.setParameter("endDate", filter.getEndDate());

    Optional.ofNullable(filter.getActive())
        .ifPresentOrElse(active -> query.setParameter("active", active),
            () -> query.setParameter("active", Boolean.TRUE));

    return query.getResultList();
  }
}
