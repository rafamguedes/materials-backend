package com.materials.api.entity;

import com.materials.api.service.dto.ItemDTO;
import com.materials.api.service.dto.UserDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.time.LocalDateTime;

@SqlResultSetMappings({
  @SqlResultSetMapping(
      name = User.USER_DTO_MAPPING,
      classes = {
        @ConstructorResult(
            targetClass = UserDTO.class,
            columns = {
              @ColumnResult(name = "id", type = Long.class),
              @ColumnResult(name = "name", type = String.class),
              @ColumnResult(name = "email", type = String.class),
              @ColumnResult(name = "registry", type = String.class),
              @ColumnResult(name = "createdAt", type = LocalDateTime.class),
              @ColumnResult(name = "updatedAt", type = LocalDateTime.class),
              @ColumnResult(name = "deletedAt", type = LocalDateTime.class),
              @ColumnResult(name = "addressId", type = Long.class)
            })
      })
})
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_users")
@EqualsAndHashCode(callSuper = true)
public class User extends GenericEntity {
  @Serial private static final long serialVersionUID = 1L;
  public static final String USER_DTO_MAPPING = "UserDTOMapping";

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;

  @Column(name = "registry")
  private String registry;

  @Column(name = "active")
  private Boolean active;

  @Column(name = "is_deleted")
  private Boolean isDeleted;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @OneToOne(cascade = CascadeType.ALL)
  private Address address;
}
