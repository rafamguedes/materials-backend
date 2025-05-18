package com.materials.api.entity;

import com.materials.api.enums.ItemStatusEnum;
import com.materials.api.enums.ItemTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_item")
public class Item {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "item_type")
  private ItemTypeEnum type;

  @Column(name = "serial_number")
  private String serialNumber;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private ItemStatusEnum status;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}
