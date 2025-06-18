package com.materials.api.entity;

import com.materials.api.security.Role;
import com.materials.api.service.dto.ItemDTO;
import com.materials.api.service.dto.UserDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

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
              @ColumnResult(name = "active", type = Boolean.class),
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
public class User extends GenericEntity implements UserDetails {
  @Serial private static final long serialVersionUID = 1L;
  public static final String USER_DTO_MAPPING = "UserDTOMapping";

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;

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

  @PrePersist
  protected void onCreate() {
    this.active = Boolean.TRUE;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
    this.isDeleted = Boolean.FALSE;
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = LocalDateTime.now();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.getName()));
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
