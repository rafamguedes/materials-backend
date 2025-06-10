package com.materials.api.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_users")
@EqualsAndHashCode(callSuper = true)
public class User extends GenericEntity {

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
